
package org.inra.yedgen.processor.entities ;

import java.util.Set ;
import java.util.Map ;
import java.util.List ;
import java.util.Arrays ;
import java.util.HashMap ;
import java.util.HashSet ;
import java.util.Objects ;
import java.util.Iterator ;
import java.util.Map.Entry ;
import java.util.ArrayList ;
import java.util.Comparator ;
import java.io.Serializable ;
import java.util.ListIterator;
import java.util.regex.Pattern ;
import java.util.stream.Collectors ;
import org.inra.yedgen.sql.SqlAnalyzer ;
import org.apache.commons.lang.StringUtils ;
import org.inra.yedgen.properties.ObdaProperties ;
import static java.util.stream.Collectors.joining ;
import org.inra.yedgen.graph.managers.GraphExtractor ;
import org.inra.yedgen.processor.managers.ManagerVariable ;

/**
 *
 * @author ryahiaoui
 */
public final class Node implements Serializable  {
    
    private final Integer hash          ;
    private final String  id            ;
    private       int     code          ;
    private       String  uri           ;
    private       String  type          ;
    private final String  label         ;
    private       String  query         ;
    private final String  uriObject     ;
    private       String  queryObject   ;
    private       String  predicat      ;
    private final String  defaultPrefix ; 
     
    private final Map<String , Set<String> > predicatsValues = new HashMap<>() ;
    
    enum TypeTriple { SUBJECT , PREDICATE , OBJECT } ;
    
    private static final String URI_VALIDATOR = 
            "^((https?|ftp|file)://|(www\\.))[-a-zA-Z0-9+&@#/%?=~_|!:,.;µs%°]*[-a-zA-Z0-9+&@#/%=~_|]" ;
            
    public Node( Integer hash          , 
                 String  id            , 
                 int     codeSubject   , 
                 String  uri           , 
                 String  type          , 
                 String  label         ,
                 String  predicat      ,
                 String  query         ,
                 Integer codeObject    ,
                 String  uriObject     ,
                 String  queryObject   ,
                 String  defaultPrefix )     {

        Objects.requireNonNull(codeSubject ) ;
        this.defaultPrefix   = defaultPrefix ;
        this.hash            = hash          ;
        this.id              = id            ;
        this.code            = codeSubject   ;       
        this.label           = label         ;
        this.query           = cleanQ(query) ;
        this.queryObject     = queryObject   ;
       
        this.uri             = validatePrefix( defaultPrefix      , 
                                               uri                ,
                                               TypeTriple.SUBJECT ) ;
        this.type            = validatePrefix( defaultPrefix        , 
                                               type                 , 
                                               TypeTriple.PREDICATE ) ;
        this.uriObject       = validatePrefix( defaultPrefix        , 
                                               uriObject            ,  
                                               TypeTriple.OBJECT    ) ;
        
        if( uri == null && codeSubject < 0 )   this.uri = type        ;
        
        if( uri != null  && ! uri.trim().equals(label.trim()) )     {
          this.addPredicatWithObject( "a", type )             ;
        }
        
        this.predicat = validatePrefix( defaultPrefix         , 
                                        predicat              , 
                                        TypeTriple.PREDICATE  ) ;
        
        if ( codeObject == null || codeObject != codeSubject )  {
             
            if (  uriObject != null                                 &&
                ! uriObject.contains("?")                           &&
                ! uriObject.equals(GraphExtractor.PATTERN_CONTEXT)  &&
                ! uriObject.equals(GraphExtractor.PATTERN_PARALLEL) ) {
            
               if( uriObject.matches( URI_VALIDATOR) )  {
                   uriObject = "<" + uriObject  + ">"   ;
               }
               else if ( isUri(uriObject) )         {
                  if ( ! uriObject.contains(":")  && 
                       ! uriObject.startsWith(":")) {
                      uriObject = ":" + uriObject   ;
                  }
               }
               else if ( ! uriObject.trim().startsWith("\"") &&
                         ! uriObject.trim().startsWith("\"") &&
                         ! uriObject.trim().contains(":") )   {
                   uriObject = "\"" + uriObject + "\"" ;
               }
            }
            
            this.addPredicatWithObject( predicat , uriObject ) ;
            
        } else {
            
            /* Recursivity - Recursion */
            
            this.predicat  = this.predicat == null ? "" :
                             this.predicat.replaceAll(" +", " ").trim()
                                 .split(Pattern.quote("{"))[0].trim() ;
             
            List<String> expp =  null ;
            
            expp = extractPredicatePatternFromRecursion ( predicat )  ;
            
            if( expp != null ) {
                this.addPredicatWithObject( this.predicat , 
                                            uriObject.replace( expp.get(0).trim()    , 
                                                               expp.get(1).trim()) ) ;
            }
        }
        
    }

    public String getLabel() {
        return label ;
    }

    public Integer getHash() {
        return hash ;
    }

    public Integer getCode() {
        return code ;
    }
    
    public String getId()    {
        return id ;
    }

    public String getUri()   {
        return uri ;
    }
    
    public String getType()  {
     if(type == null ) return ""      ;
     return type.replaceAll(" ", "" ) ;
    }

    public String getQuery() {
        return query ;
    }

    public String getUriObject()     {
        return uriObject ;
    }

    public String getQueryObject()   {
        return queryObject ;
    }

    public String getPredicat()      {
        return predicat;
    }
    public String getDefaultPrefix() {
        return defaultPrefix ;
    }

    public Map<String, Set<String>> getPredicatsValues() {
        return predicatsValues ;
    }
    
    public Map<String, Set<String>> getPredicatsValuesIgnoringType() {
        
        return predicatsValues.entrySet().stream()
                              .filter(entry -> !entry.getKey().startsWith("a"))
                              .collect(Collectors.toMap(e -> e.getKey(), e -> new HashSet(e.getValue()))) ;
    }

    public boolean hasPredicateWithValue ( String value ) {
        
        return predicatsValues.values()
                              .stream()
                              .anyMatch( set -> set.contains(value) ) ;
    }
    
    public String getPredicatContainingValue ( String value ) {
        
        return predicatsValues.entrySet()
                              .stream()
                              .filter( entry -> entry.getValue().contains(value) )
                              .map( s -> s.getKey() ).findFirst().orElse(null)   ;
    }

    
    public void addPredicatWithObject( String predicat, String object ) {
    
        if(predicat == null || object == null ) return ;
        
        predicat = validatePrefix(defaultPrefix, predicat , TypeTriple.PREDICATE ) ;
        object   = validatePrefix(defaultPrefix, object   , TypeTriple.OBJECT )   ;
        
        if( predicatsValues.containsKey(predicat) )     {
            predicatsValues.get(predicat).add( object ) ;
        }
        else {
            Set<String> values = new HashSet()     ;
            values.add( object )                   ;
            predicatsValues.put(predicat, values ) ;
        }
    }
    
    public void addPredicatWithObjects( Map<String , Set<String> > predicatsVals ) {

         // predicatsVals = Predicat + Set of Objects URI
         
        predicatsVals.entrySet().stream().forEach( 
               entry -> {
                   String key = entry.getKey() ;
                   if( this.predicatsValues.containsKey(key)) {
                       this.predicatsValues.get(key).addAll(predicatsVals.get(key)) ;
                   }
                   else {
                       this.predicatsValues.put(key, predicatsVals.get(key)) ;
                   }
               }
        ) ;
    }

    @Override
    public int hashCode() {
        int hash = 7 ;
        return hash  ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true ;
        }
        if (obj == null) {
            return false ;
        }
        if (getClass() != obj.getClass()) {
            return false ;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.hash, other.hash)) {
            return false ;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false ;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false ;
        }
        return true ;
    }
    
    /**
     *
     * @return
     */
    public Node copy() {
      return (Node) org.apache.commons.lang.SerializationUtils.clone(this) ;
    }
    
    @Override
    public String toString() {
       
           return " Node { "         + 
                  " Hash = "         + hash            +
                  ", Id = "          + id              +
                  ", Code = "        + code            +
                  ", Uri = "         +  uri            +
                  ", Type = "        + type            + 
                  ", Label = "       + label           +
                  ", Predicat = "    + predicatsValues +
                  ", Query = "       + query           +
                  ", uriObject = "   + uriObject       +
                  ", queryObject = " + queryObject     +
                  " } " ;
    }
    
    
    public String outputTurtle () {
    
      return
          uri + " " + 
          predicatsValues.entrySet()
                         .stream()
                         .map( entry -> entry.getKey() + " " + String.join( " , ", entry.getValue()))
                         .collect(joining(" ; ")) + " . " ;
    }

    public String outputObda() {
   
         return formatObda ( 
          code ,
          uri + " " + 
          predicatsValues.entrySet()
                         .stream()
                         .map( entry -> entry.getKey() + " " + String.join( " , ", entry.getValue()))
                         .collect(joining(" ; ")) + " . "  ,
          query  ) ;
    }

    public String outputOnlyPredicatesValues() {
    
      return
          predicatsValues.entrySet()
                         .stream()
                         .map( entry -> entry.getKey().startsWith("a") ? "" :
                               entry.getKey() + " " + String.join( " , ", entry.getValue()) )
                         .collect(joining(" ; ")) + " . " ;
    }
 
    public void updatePatternValues( String pattern, Map<String, Set<String>> patternContextValues ) {

        Entry<String, Set<String>> predicatKey = 
                predicatsValues.entrySet()
                               .stream()
                               .filter( entry -> entry.getValue().contains(pattern) )
                               .map( entry -> { entry.getValue().remove(pattern) ; return entry ; } )
                               .findFirst()
                               .orElse(null) ;
        
      Set<String> remove = predicatsValues.get(predicatKey.getKey()).isEmpty() ? 
                           predicatsValues.remove(predicatKey.getKey()) :  null ;
      this.addPredicatWithObjects(patternContextValues ) ;
    
    }
    
    
    public void removeEmptyOptionalEntry( String value ) {

        predicatsValues.entrySet()
                       .stream()
                       .filter( entry -> entry.getValue().contains(value) )
                       .map (   entry -> { entry.getValue().remove(value) ; return entry ; } )
                       .map (   entry -> { return entry.getKey() ; } )
                       .count() ;
                
        predicatsValues.values().removeIf( set ->  set.isEmpty() ) ;
      
    }

    public void updatePatternValue( String pattern, String uri ) {

        Entry<String, Set<String>> predicatKey = 
                
                predicatsValues.entrySet()
                               .stream()
                               .filter( entry -> entry.getValue().contains(pattern) )
                               .map( entry -> { entry.getValue().remove(pattern) ; return entry ; } )
                               .findFirst()
                               .orElse(null) ;
        
      Set<String> remove = predicatsValues.get(predicatKey.getKey()).isEmpty() ? 
                           predicatsValues.remove(predicatKey.getKey()) : null ;
      this.addPredicatWithObject(predicatKey.getKey() , uri )                  ;
    
    }

    public void removePredicat( String predicat ) {
       this.predicatsValues.remove(predicat)      ;
    }
    
    private String validatePrefix ( String     defaulPrefix , 
                                    String     entity       ,
                                    TypeTriple type   )     {
         
        if( entity != null                 &&
            entity.trim().startsWith("\"") && 
            entity.trim().endsWith("\"")   && 
            type == TypeTriple.OBJECT )     {
            
            return entity ;
        }

        if ( entity == null                 || 
             entity.startsWith("?")         ||
             entity.startsWith("##PATTERN") ||
             entity.startsWith("<")         ||
             entity.trim().equals ("a") 
           )  
           
           return entity ;
        
        if ( entity.contains("/") )   return entity.startsWith(":") ? entity : ":" + entity ;
        if ( entity.contains(":") )   return entity ;
        
        return defaulPrefix == null ? ":" + entity : defaulPrefix + ":" + entity ;
        
    }
   
    private boolean isUri( String uri) {
        return  uri.contains(":") || 
                uri.contains("/") ||
                uri.startsWith("<") && uri.endsWith(">") ;
    }
    
    public void applyKeyValue ( String pattern , String value ) {
        
        if( value  == null     ||
            value.isEmpty()    || 
            value.equals(":") ) {
            
            value = ManagerVariable.OPTIONAL_NODE ;
        }
        
        for (Iterator< Set<String> > iterator = this.predicatsValues.values().iterator(); iterator.hasNext() ; ) {
             
            Set<String> set   = iterator.next()      ;
            
            List<String> list = new ArrayList<>(set) ;
            
            for ( ListIterator<String> it = list.listIterator() ; it.hasNext() ; )   {
                
                 String line = it.next()             ;
                 
                 if ( matchesStringPattenrn( line, pattern ) ) {
                                     
                    // it.add(line.replace( pattern , 
                    //                   isUri(line) ? cleanValue(value) : value  )) ;
                    
                    line = line.replace( pattern , cleanValue( value ) ) ;
                    
                    if ( line.matches(URI_VALIDATOR) ) {
                         line = "<" + line + ">" ;                     
                    } 
                    else if ( isUri(line))         {
                      if ( ! line.contains(":")   &&  
                           ! line.startsWith(":")) {
                         line = ":" + line    ;
                    }
                    }
                    else if ( ! line.contains(":")    && 
                              ! line.startsWith("\"") && 
                              ! line.endsWith("\"") )  {
                         line = "\"" + line   + "\""   ; 
                    }
                    
                    it.remove()    ;
                    it.add( line ) ;
                }
            }
            
            set.clear()  ;
            
            list.forEach( (i) -> {   set.add(i) ; } ) ;
        }
        
        if ( matchesStringPattenrn( uri , pattern ) ) {
           //uri = uri != null ? uri.replace( pattern, cleanValue(value) ) : uri ;
           uri = uri != null ? uri.replace( pattern, cleanValue(value) ) : uri ;          
        }
        
        if ( uri.matches( URI_VALIDATOR ) ) {
             uri = "<" + uri + ">" ;
        }
        else if( ! uri.contains(":")   && 
                 ! uri.startsWith("<") && 
                 ! uri.endsWith(">") )  {
             uri = ":" + uri          ;
        }        
        
        if ( matchesStringPattenrn( type , pattern ) ) {
           type =  type != null ? type.replace ( pattern, value ) : type       ;
        }
        
        if ( matchesStringPattenrn( query , pattern ) ) {
           query =  query       != null ? query.replace ( pattern, value ) : query      ;
        }
        
        if ( matchesStringPattenrn( predicat , pattern ) ) {
           predicat =  predicat != null ? predicat.replace( pattern, value ) : predicat ;
        }
        if ( matchesStringPattenrn( queryObject , pattern ) ) {
           queryObject =  queryObject != null ? queryObject.replace( pattern, value ) : queryObject ;
        }
        
    }

    public void applyKeyValues ( Map<String, String > values )  {
    
       // Comparator< Entry<String,String> > comp1 = 
       // (a, b) -> Integer.compare(a.getKey().length(), b.getKey().length()) ;
      
        Comparator< Entry<String,String> > comp = Comparator.comparing( e -> e.getKey().length()) ;
         
        values.entrySet()
              .stream()
              .sorted(comp.reversed())
              .forEach( entry -> applyKeyValue(entry.getKey(),entry.getValue())) ;
    }

    public void addToCode( int number ) {
        this.code += number ;
    }

    private String formatObda( Integer code, String target, String query) {
    
      return ObdaProperties.MAPPING_COLLECTION_PATTERN
                           .replace("?id", getKeyByURI( "(" + String.valueOf(code) + ")_" + uri ))
                           .replace("?target", target )
                           .replace( "?source", query == null ? "null" : query  )   +  "\n"      ;
    }
    
    private String getKeyByURI(String target )            {

        return StringUtils.removeEnd (
                target.replaceAll(Pattern.quote("/{"), "_")
                      .replaceAll(Pattern.quote("-{"), "_")
                      .replaceAll(Pattern.quote("/"), "_" )
                      .replaceAll(Pattern.quote("{"), "_" )
                      .replaceAll(Pattern.quote("}"), "_" )
                      .replaceAll(Pattern.quote(":"), "_" )
                      .replaceAll("_+", "_")             
               , "_") ;
       
   }
   
   private String cleanQ ( String query ) {
     return query == null ? null : 
                     query.replaceAll("\n", " ")
                          .replaceAll("[\n\r]", "")
                          .replaceAll(" +", " ") ;
    
   }
   
   private List<String> extractPredicatePatternFromRecursion( String predicate ) {
       
    // Syntax exp : hasContext { bloc_absolute_id > parent_absolute_id }
    // index 0 : sourcerPredicatePattern
    // index 1 : targetPredicatePattern
       
     if ( predicate == null ) return null ;
       
     if( ! predicate.matches ("\\w+\\s*\\{{1}\\s*\\w+\\s*\\>{1}\\s*\\w+\\s*\\}{1}$") ) {
            System.out.println( " " )                                      ;
            System.out.println( " Error when parsing syntax Recursion " )  ;
            System.out.println( " May Be : \n "
                                + "  1- You have a recursion with a bad syntax on"
                                + " the Node having the Code [ " + code + " ] \n "
                                + "  2- You have 2 nodes with the same Code [ " + code + " ] \n "
                                + "  3- Syntax Exp : hasContext { bloc_absolute_id > parent_absolute_id } " ) ;
            System.exit( 2)   ;   
     }
  
     return  Arrays.asList(predicate.replace("}", "")
                   .replaceAll(" +", " ")
                   .trim()
                   .split(Pattern.quote("{"))[1].split(">")) ;    
   }
      
   public static String cleanValue( String value ) {
        
      // if (value.matches(URI_VALIDATOR))  return value ;
       
      return value.startsWith(":")        ?
             value.replace(":" , "")
                  .replace("'" , "")
                  .replace("\"", "")
                  .replaceAll(" +" , "")  :
             value.replace(":" , "-")
                  .replace("'" , "")
                  .replace("\"", "")
                  .replaceAll(" +" , "")  ;
   }
      
   public void applyToQuery(String filterQuery ) {
       
        if(filterQuery != null ) {
            query = SqlAnalyzer.treatQuery(getQuery(), filterQuery ) ;
        }
        
   }
   
   // matchesStringContainsPatternFollowedByNothingButSpecialChars 
   static boolean matchesStringPattenrn ( String line    , 
                                          String pattern ) {
     if( line == null || pattern == null ) return false ;
     if( ! pattern.trim().startsWith("?")) return false ;
     // the line must contains the pattern and not followed with [a-zA-Z]
     String patt = ".*(" + pattern.replace("?", "\\?") +")(?![a-zA-Z]).*$" ;
     return  line.matches(patt ) ;
        
   }
}

