

package org.inra.yedgen.graph.managers;

import org.json.XML;
import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Locale;
import java.util.Arrays;
import java.util.Objects;
import java.util.HashMap;
import java.util.HashSet;
import org.json.JSONArray;
import java.nio.file.Path;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.logging.Logger;
import org.inra.yedgen.processor.io.Writer;
import org.inra.yedgen.processor.Processor;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author ryahiaoui
 */
public class GraphManager_tmp {
 
    
//    private final Set<Edge>               edges                   =  new  HashSet<>()   ;
//    private final Map<String , Concept >  concepts                =  new  HashMap<>()   ;
//    private final Map<String , String>    prefix                  =  new  HashMap<>()   ;
//    private final Map<Integer, String>    tmpUris                 =  new  HashMap<>()   ;
//    
//    private final Map<String,  String>  uris_queries              =  new  HashMap<>()   ;
//    
//    private final Map<String,  String>  uris_queries_parallel     =  new  HashMap<>()   ;
//    private final Map<String,  String>  uris_queries_parallel_key =  new  HashMap<>()   ;
//    private final Set<String>           uris_parallel_root        =  new  HashSet<>()   ;
//    
//    private final Map<String,  Integer> uris_num                  =  new  HashMap<>()   ;
//    private final Map<String,  Integer> uris_num_parallel         =  new  HashMap<>()   ;
//    
//    private final Map<Integer, String>  source                    =  new  HashMap<>()   ;
//    private final Map<Integer, Integer> sourceByCode              =  new  HashMap<>()   ;
//      
//    private final Map<String,  String>  target                    =  new  HashMap<>()   ;
//    private final Map<String,  String>  target_parallel           =  new  HashMap<>()   ;
//    private final Map<String,  String>  SourceDeclaration         =  new  HashMap<>()   ;
//    
//    private final Map<String,  String>  PATTERNS_CONTEXT          =  new  HashMap<>()   ;
//    private final Map<String,  String>  PATTERNS_PARALLEL         =  new  HashMap<>()   ;
//    
//    private final List<String>          VARIABLES                 =  new  ArrayList<>() ;
//
//    private static String  PREFIX_PREDICAT         =  "oboe-coreX"                  ;
//    private static final String  PREFIXDECLARATION = "[PrefixDeclaration]"          ;
//    private static final String  PREF              =  "?pref		?uri"       ;
//
//    private static final String MAPPING_COLLECTION_BEGIN   = "[MappingDeclaration] @collection [[" ;
//
//    private static final String MAPPING_COLLECTION_PATTERN =  "mappingId	?id\n"      +
//                                                              "target		?target\n"  +
//                                                              "source		?source"    ;
//
//    private static final String MAPPING_COLLECTION_END     = "]]" ;
//
//    private boolean existHeader      = false                  ;
//    private boolean isGraphPattern   = false                  ;
//    
//    private static final String  MATCHER_VARIABLE         = "?VARIABLE"          ;
//    private static final String  MATCHER_ENTITY           = "?ENTITY"            ;
//    private static final String  MATCHER_PATTERN_CONTEXT  = "##PATTERN_CONTEXT"  ;
//    private static final String  MATCHER_PATTERN_PARALLEL = "##PATTERN_PARALLEL" ;
//    private static final String  OF_ENTITY_PATTERN        = "oboe-core:ofEntity" ;
//    
//    String linker  = null ;
//    
//    private static final String         META_PATTERN_CONTEXT  = "##META_PATTERN_CONTEXT"  ;
//    private static final String         META_PATTERN_PARALLEL = "##META_PATTERN_PARALLEL" ;
//    private static final String         META_VERIABLE         = "?META_VARIABLE"          ;
//    private final Map<String,  String>  meta_paterns          =  new  HashMap<>()         ;
//    
//    private final String SEPARATOR                            = "\t"                      ;
//    
//    private  JSONObject loadJsonObject ( String pathFile ) throws IOException {
//
//        String xml ;
//
//        try ( InputStream inputStream = new FileInputStream(pathFile) )  {
//
//            StringBuilder builder = new StringBuilder() ;
//
//            int ptr ;
//
//            while ((ptr = inputStream.read()) != -1 )
//            {
//                builder.append((char) ptr) ;
//            }
//
//            xml = builder.toString() ;
//        }
//
//        return XML.toJSONObject(xml) ;
//    }
//
//
//    private void loadConcepts ( JSONObject jsonObj , int hash )   {
//
//        JSONArray jsonArrayConcepts = jsonObj.getJSONObject("graphml")
//                                             .getJSONObject( "graph" )
//                                             .getJSONArray( "node" ) ;
//
//        for (int i = 0; i < jsonArrayConcepts.length(); i++)      {
//
//            Object obj                 = jsonArrayConcepts.get(i) ;
//            
//            JSONObject jsonObjectConcept  = (JSONObject) obj      ;
//
//            if(obj != null) {
//
//                if(obj.toString().startsWith("{\"data\":{")) {
//
//                    String label = jsonObjectConcept.getJSONObject("data")
//                                                    .getJSONObject("y:ShapeNode")
//                                                    .getJSONObject("y:NodeLabel")
//                                                    .getString("content").trim()
//                                                    .replaceAll(" +", " ") ;
//  
//                    String id       =  jsonObjectConcept.getString("id") + "_"+ hash ;
//                    String type     =  null ;
//                    int    code     =  -1   ;
//
//                    if(label.contains("(") && label.contains(")")) {
//                        code =  Integer.parseInt(
//                                  label.split(Pattern.quote("("))[1]
//                                       .replaceAll("[^0-9]", ""))   ;
//                        type = label.split(Pattern.quote("("))[0]  ;
//                    }
//                   
//                    Concept concept ;
//                    if(code == -1 && ! label.startsWith(MATCHER_PATTERN_CONTEXT) 
//                                  && ! label.startsWith(MATCHER_PATTERN_PARALLEL ) ) {
//                        concept = new Concept (id, code + hash , code , type, label ) ;
//                    }
//                    else {
//                        if(label.startsWith(":"))
//                            concept = new Concept( id, code + hash, code,
//                                    type, label.split(Pattern.quote("("))[0] + "::#" ) ;
//                        else
//                            concept = new Concept ( id, code + hash, code, type,
//                                              label.split(Pattern.quote("("))[0] )         ;
//                    }
//
//                    concepts.put(id, concept) ;
//                }
//
//                if ( jsonObjectConcept.has("graph")) {
//
//                    if ( jsonObjectConcept.getJSONObject("graph").toString().startsWith("{\"node\":[")) {
//
//                        JSONArray jsonArrayGroupConcepts =
//                                jsonObjectConcept.getJSONObject("graph")
//                                                 .getJSONArray("node") ;
//
//                        for (int j = 0; j < jsonArrayGroupConcepts.length(); j++) {
//
//                            if ( jsonArrayGroupConcepts.toString().startsWith("{\"data\":[")   ||
//                                    jsonArrayGroupConcepts.toString().startsWith("[{\"data\":[") ) {
//
//                                if ( jsonArrayGroupConcepts.getJSONObject(j)
//                                                        .getJSONArray("data")
//                                                        .getJSONObject(1)
//                                                        .has("y:ShapeNode")) {
//
//                                    String id = jsonArrayGroupConcepts.getJSONObject(j)
//                                                .getString("id") ;
//
//                                    String label = jsonArrayGroupConcepts.getJSONObject(j)
//                                                                      .getJSONArray("data")
//                                                                      .getJSONObject(1)
//                                                                      .getJSONObject("y:ShapeNode")
//                                                                      .getJSONObject("y:NodeLabel")
//                                                                      .getString("content").trim()
//                                                                      .replaceAll(" +", " ") ;
//                                     
//                                    int code ;
//
//                                    if (label.toLowerCase(Locale.FRENCH).startsWith("query_(")) {
//                                        code =  Integer.parseInt(label
//                                                       .split(Pattern.quote(":"))[0]
//                                                       .split(Pattern.quote("_"))[1]
//                                                       .replaceAll("[^0-9]", ""))  ;
//
//                                        source.put(code+hash, label.split( Pattern
//                                                                   .quote(": "))[1]
//                                                                   .trim())       ;
//                                                                   
//                                        sourceByCode.put(code, code + hash )      ;
//                                    }
//                                    
//                                    else if (label.toLowerCase()
//                                                  .startsWith("(") && 
//                                                  label.toLowerCase().contains(")") ) {
//                                                      
//                                        code =  Integer.parseInt(label
//                                                       .split(Pattern.quote(")"))[0]
//                                                       .replaceAll("[^0-9]", ""))  ;
//
//                                        tmpUris.put(code + hash , label.split( Pattern
//                                                                       .quote(")"))[1]
//                                                                       .trim())      ;
//                                    }
//                                }
//                            }
//
//                            else
//
//                            if ( jsonArrayGroupConcepts.toString().startsWith("{\"data\":{")   ||
//                                 jsonArrayGroupConcepts.toString().startsWith("[{\"data\":{") ) {
//
//                                if ( jsonArrayGroupConcepts.getJSONObject(j)
//                                                        .getJSONObject("data")
//                                                        .has("y:ShapeNode"))       {
//
//                                    String id = jsonArrayGroupConcepts.getJSONObject(j)
//                                                                   .getString("id") ;
//                                    
//                                    String label = jsonArrayGroupConcepts.getJSONObject(j)
//                                                                      .getJSONObject("data")
//                                                                      .getJSONObject("y:ShapeNode")
//                                                                      .getJSONObject("y:NodeLabel")
//                                                                      .getString("content").trim()
//                                                                      .replaceAll(" +", " ") ;
//                                    
//                                    if (label.startsWith(MATCHER_PATTERN_CONTEXT) && label.contains(" ")) {
//                                            isGraphPattern = true     ;
//                                            PATTERNS_CONTEXT.put(label.split(" ")[0], 
//                                                         label.replaceFirst(Pattern.quote(label
//                                                              .split(" ")[0]),"").trim()) ;
//                                    }
//                                    if (label.startsWith(MATCHER_PATTERN_PARALLEL) && label.contains(" ")) {
//                                            isGraphPattern = true     ;
//                                            PATTERNS_PARALLEL.put(label.split(" ")[0] ,
//                                                         label.replaceFirst(Pattern.quote(label
//                                                              .split(" ")[0]),"").trim()) ;
//                                    }
//                                    
//                                    else if (label.startsWith(MATCHER_VARIABLE) && label.contains(" ")) {                                           
//                                            VARIABLES.add(label.replaceFirst(Pattern.quote(MATCHER_VARIABLE),"")) ;
//                                    }
//                                    
//                                    else if (label.startsWith(META_VERIABLE) && label.contains(" ")) {                                           
//                                            meta_paterns.put(META_VERIABLE, label.replaceFirst(Pattern.quote(META_VERIABLE),"")) ;
//                                    }
//                                    else if (label.startsWith(META_PATTERN_CONTEXT) && label.contains(" ")) {                                           
//                                            meta_paterns.put(META_PATTERN_CONTEXT, label.replaceFirst(Pattern.quote(META_PATTERN_CONTEXT),"")) ;
//                                    }
//                                    else if (label.startsWith(META_PATTERN_PARALLEL) && label.contains(" ")) {                                           
//                                            meta_paterns.put(META_PATTERN_PARALLEL, label.replaceFirst(Pattern.quote(META_PATTERN_PARALLEL),"")) ;
//                                    }
//
//                                    int code ;
//
//                                    if (label.toLowerCase().startsWith("query_(")) {
//                                        code =  Integer.parseInt(label
//                                                       .split(Pattern.quote(":"))[0]
//                                                       .split(Pattern.quote("_"))[1]
//                                                       .replaceAll("[^0-9]", ""))  ;
//
//                                        source.put(code+hash, label.split(Pattern
//                                              .quote(": "))[1].trim())             ;
//                                              
//                                        sourceByCode.put(code, code + hash )       ;
//                                    }
//                                    
//                                    else
//                                    
//                                    if ( label.toLowerCase().startsWith("(") 
//                                         && label.toLowerCase().contains(")") )   {
//                                                
//                                        code =  Integer.parseInt(label
//                                                       .split(Pattern.quote(")"))[0]
//                                                       .replaceAll("[^0-9]", ""))  ;
//
//                                        tmpUris.put(code+hash, label.split(Pattern
//                                                                    .quote(")")) [1]
//                                                                    .trim())       ;
//
//                                        int co = Integer.parseInt(label
//                                                        .split(Pattern
//                                                        .quote(") ")) [0].trim()
//                                                        .replace("(", ""))     ;
//                                        
//                                        if ( uris_num.values().contains( co ) ) {
//                                          System.out.println("ALERT # Code : " + co + " Detected multiple times ! ") ;
//                                        }
//                                         
//                                        uris_num.put( ":" + label.split( Pattern
//                                                                .quote(")")) [1]
//                                                                .trim() , co ) ;
//                                    }
//                                    else
//                                    if (label.toLowerCase().startsWith("prefix ")) {
//                                        String pref = label.split(Pattern.quote(" "))[1] ;
//                                        String uri  = label.split(Pattern.quote(" "))[2] ;
//
//                                        prefix.put(pref, uri) ;
//                                    }
//                                    else
//                                    if (label.startsWith("PREDICAT_PREFIX :"))       {
//                                        
//                                        PREFIX_PREDICAT = label.split(Pattern
//                                                               .quote("PREDICAT_PREFIX :"))[1] ;
//                                        
//                                    }
//                                    else
//                                    if (label.toLowerCase().startsWith("obda-"))    {
//
//                                        if  ( label.split(Pattern.quote(" : ")) [0]
//                                                   .equals("obda-sourceUri"))     {
//                                                       
//                                            SourceDeclaration.put("sourceUri",
//                                                    label.split(Pattern
//                                                         .quote(" : "))[1]) ;
//                                        }
//                                        else if ( label.split(Pattern.quote(" : ")) [0]
//                                                       .equals("obda-connectionUrl")) {
//                                                  
//                                            SourceDeclaration.put("connectionUrl", label
//                                                             .split(Pattern
//                                                             .quote(" : "))[1]) ;
//                                        }
//                                        else if (label.split(Pattern.quote(" : "))[0]
//                                                      .equals("obda-username"))     {
//                                                        
//                                            SourceDeclaration.put("username",  label
//                                                             .split(Pattern
//                                                             .quote(" : "))[1])    ;
//                                        }
//                                        else if (label.split(Pattern.quote(" : "))[0]
//                                                      .equals("obda-password"))     {
//                                                  
//                                            SourceDeclaration.put("password", label
//                                                             .split(Pattern
//                                                             .quote(" : "))[1])   ;
//                                        }
//                                        else if ( label.split(Pattern.quote(" : "))[0]
//                                                       .equals("obda-driverClass"))  {
//                                                        
//                                            SourceDeclaration.put("driverClass", label
//                                                             .split(Pattern
//                                                             .quote(" : "))[1])   ;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    else
//
//                    if(jsonObjectConcept.getJSONObject("graph")
//                                     .toString().startsWith("{\"node\":{"))    {
//
//                        JSONObject jsonArrayGroupNodes = jsonObjectConcept.
//                                                         getJSONObject("graph" )
//                                                        .getJSONObject("node") ;
//
//                        if( jsonArrayGroupNodes.getJSONObject("data").has("y:ShapeNode")) {
//
//                            String id = jsonArrayGroupNodes.getString("id") ;
//
//                            String label = jsonArrayGroupNodes
//                                            .getJSONObject("data")
//                                            .getJSONObject("y:ShapeNode")
//                                            .getJSONObject("y:NodeLabel")
//                                            .getString("content").trim()
//                                            .replaceAll(" +", " ") ;
//
//                            int code ;
//
//                            if (label.startsWith(MATCHER_PATTERN_CONTEXT) && label.contains(" ")) {
//                                    isGraphPattern = true     ;
//                                    PATTERNS_CONTEXT.put(label.split(" ")[0], label.replaceFirst(label
//                                                                           .split(" ")[0],"")
//                                                                           .trim()) ;
//                            }
//                            else if (label.startsWith(MATCHER_PATTERN_PARALLEL) && label.contains(" ")) {
//                                            isGraphPattern = true     ;
//                                            PATTERNS_PARALLEL.put(label.split(" ")[0] ,
//                                                         label.replaceFirst(Pattern.quote(label
//                                                              .split(" ")[0]),"").trim()) ;
//                            }
//                            else if (label.startsWith(MATCHER_VARIABLE) && label.contains(" ")) {                                  
//                                  VARIABLES.add(label.replaceFirst(label.split(" ")[0],"")) ;
//                            }
//                            
//                            else if (label.startsWith(META_VERIABLE) && label.contains(" ")) {                                           
//                                  meta_paterns.put(META_VERIABLE, label.replaceFirst(Pattern.quote(META_VERIABLE),"")) ;
//                            }
//                            else if (label.startsWith(META_PATTERN_CONTEXT) && label.contains(" ")) {                                           
//                                  meta_paterns.put(META_PATTERN_CONTEXT, label.replaceFirst(Pattern.quote(META_PATTERN_CONTEXT),"")) ;
//                            }
//                            else if (label.startsWith(META_PATTERN_PARALLEL) && label.contains(" ")) {                                           
//                                  meta_paterns.put(META_PATTERN_PARALLEL, label.replaceFirst(Pattern.quote(META_PATTERN_PARALLEL),"")) ;
//                            }
//
//                            if(label.toLowerCase().startsWith("query_("))  {
//                                code =  Integer.parseInt(label
//                                               .split(Pattern.quote(":"))[0]
//                                               .split(Pattern.quote("_"))[1]
//                                               .replaceAll("[^0-9]", ""))  ;
//
//                                source.put(code+hash, label.split(Pattern
//                                                           .quote(": "))[1]
//                                                           .trim())       ;
//                                                           
//                                sourceByCode.put(code, code + hash )      ;
//                            }
//                            else
//                            if( label.toLowerCase().startsWith("(") 
//                                && label.toLowerCase().contains(")") )   {
//                                      
//                                code =  Integer.parseInt(label
//                                               .split(Pattern.quote(")"))[0]
//                                               .replaceAll("[^0-9]", ""))  ;
//
//                                tmpUris.put( code+hash, label.split(Pattern
//                                                             .quote(")"))[1]
//                                                             .trim()) ;
//
//                                if (uris_num.values().contains( code )) {
//                                   System.out.println("ALERT # Code { " + code + " } Detected multiple times ! ") ;
//                                }
//                                         
//                                uris_num.put( ":" + label.split( Pattern
//                                                        .quote(")")) [1]
//                                                        .trim() , code ) ;
//                            }
//                            
//                            else
//                            
//                            if(label.toLowerCase().startsWith("prefix "))        {
//                                String pref = label.split(Pattern.quote(" "))[1] ;
//                                String uri  = label.split(Pattern.quote(" "))[2] ;
//                                prefix.put(pref, uri) ;
//                            }
//                            
//                            else
//                            
//                            if(label.startsWith("PREDICAT_PREFIX :"))       {
//                                PREFIX_PREDICAT = label.split(Pattern
//                                                       .quote("PREDICAT_PREFIX :"))[1]
//                                                       .trim() ;
//                            }
//
//                        } // ShapeNode
//                    }     // isNode Object
//                }         // has Graph
//            }             // dif null
//        }                 // boucle iterator
//    }
//
//
//    private void loadEdges ( JSONObject jsonObj, int hash ) {
//
//        if ( ! jsonObj.getJSONObject("graphml")
//                      .getJSONObject("graph")
//                      .has("edge") )    {
//           return ;
//        }
//
//        JSONArray jsonArrayEdges = new JSONArray() ;
//
//        if(jsonObj.getJSONObject("graphml")
//                  .getJSONObject("graph")
//                  .get("edge").toString().startsWith("{\"data\""))
//        {
//            jsonArrayEdges.put(jsonObj.getJSONObject("graphml")
//                          .getJSONObject("graph")
//                          .getJSONObject("edge"));
//        }
//
//        else {
//
//            jsonArrayEdges = jsonObj.getJSONObject("graphml")
//                                    .getJSONObject("graph")
//                                    .getJSONArray("edge")   ;
//        }
//
//        
//        for (int i = 0; i < jsonArrayEdges.length(); i++ ) {
//
//            Object obj = jsonArrayEdges.get(i)        ;
//
//            JSONObject jsonObject  = (JSONObject) obj ;
//
//            if(obj.toString().startsWith("{\"data\":{")) {
//
//                if(jsonObject.getJSONObject("data")
//                             .has("y:PolyLineEdge"))
//                {
//                    String predicat = jsonObject.getJSONObject("data")
//                                                .getJSONObject("y:PolyLineEdge")
//                                                .getJSONObject("y:EdgeLabel")
//                                                .getString("content") ;
//
//                    String id    = jsonObject.getString("id")     + "_" + hash  ;
//
//                    String sujet = jsonObject.getString("source") + "_" + hash  ;
//
//                    String objet = jsonObject.getString("target") + "_" + hash  ;
//
//                    Edge       e = new Edge(id, sujet, predicat, objet)         ;
//
//                    edges.add(e) ;
//
//                }
//                
//                else if ( jsonObject.getJSONObject("data").has("y:ArcEdge") )  {
//
//                    String id    = jsonObject.getString("id")     + "_" + hash ;
//
//                    String sujet = jsonObject.getString("source") + "_" + hash ;
//
//                    String objet = jsonObject.getString("target") + "_" + hash ;
//
//                    String predicat = jsonObject.getJSONObject("data")
//                                                .getJSONObject("y:ArcEdge")
//                                                .getJSONObject("y:EdgeLabel")
//                                                .getString("content")          ;
//
//
//                    Edge e = new Edge(id, sujet, predicat, objet)              ;
//                    edges.add( e)                                              ;
//                    
//                }
//                else {
//                    
//                    System.err.println(" ") ;
//                    System.err.println(" Oops something went wrong !! ")       ;
//                    System.err.println(" ") ;
//                }
//
//            }
//            
//            else
//            
//            if(obj.toString().startsWith("{\"data\":[")) {
//
//                String predicat  = "" ;
//
//                if(jsonObject.getJSONArray("data")
//                             .getJSONObject(1).has("y:PolyLineEdge"))
//                {
//                    predicat = jsonObject.getJSONArray("data")
//                                         .getJSONObject(1)
//                                         .getJSONObject("y:PolyLineEdge")
//                                         .getJSONObject("y:EdgeLabel")
//                                         .getString("content") ;
//                }
//                else if(jsonObject.getJSONArray("data")
//                                  .getJSONObject(1).has("y:QuadCurveEdge"))
//                {
//                    predicat = jsonObject.getJSONArray("data")
//                                         .getJSONObject(1)
//                                         .getJSONObject("y:QuadCurveEdge")
//                                         .getJSONObject("y:EdgeLabel")
//                                         .getString("content") ;
//                }
//
//                else if(jsonObject.getJSONArray("data")
//                                  .getJSONObject(1).has("y:ArcEdge"))
//                {
//                    if(jsonObject.getJSONArray("data")
//                                 .getJSONObject(1)
//                                 .getJSONObject("y:ArcEdge")
//                                 .has("y:EdgeLabel"))
//                    {
//                        predicat = jsonObject.getJSONArray("data")
//                                             .getJSONObject(1)
//                                             .getJSONObject("y:ArcEdge")
//                                             .getJSONObject("y:EdgeLabel")
//                                             .getString("content") ;
//                    }
//                }
//                else {
//                    System.out.println("Label not Found !!") ;
//                }
//
//                String id    = jsonObject.getString("id")     + "_" + hash ;
//
//                String sujet = jsonObject.getString("source") + "_" + hash ;
//
//                String objet = jsonObject.getString("target") + "_" + hash ;
//
//                Edge   e     = new Edge(id, sujet, predicat, objet)        ;
//
//                edges.add(e) ;
//
//            }
//            else {
//                System.err.println(" ")          ;
//                System.err.println(" Oups !! " ) ;
//                System.err.println(" ")          ;
//            }
//        }
//    }
//
//    private boolean existPrefixStartWith ( String label ) {
//        return prefix.keySet()
//                     .stream()
//                     .filter( pref -> !label.endsWith("::#"))
//                     .filter(label::startsWith)
//                     .findFirst()
//                     .isPresent() ;
//    }
//    
//    /* Write OBDA FILE */
//
//    private void treatParallelPatterns( int hash ) {
//     
//        for (Map.Entry<String, String > patt : PATTERNS_PARALLEL.entrySet()) {
//            
//            String key     = patt.getKey()   ;
//            String pattern = patt.getValue() ;
//            
//            String[] entities = pattern.split(Pattern.quote(".") ) ;
//            
//            boolean root = false ;
//            
//            for( String entity : entities ) {
//                
//                String[] subEntity = entity.trim().split(" ") ;
//                
//                String value = "" , uri = "" , tmpUri = ""  ;
//                int codeQuery = -1 ;
//                
//                for( int i = 0 ; i < subEntity.length ; i++ ) {
//                 
//                    String token = subEntity[i] ;
//                    
//                    if( token.equals(",") || token.equals(";") ) {
//                        value += token + " " ;
//                        continue       ;
//                    }
//                    String type     =  null     ;
//                    int code        =  -1       ;
//
//                    if(token.contains("(") && token.contains(")")) {
//                         code =  Integer.parseInt(
//                                   token.split(Pattern.quote("("))[1]
//                                        .replaceAll("[^0-9]", ""))  ;
//                         type = token.split(Pattern.quote("("))[0]  ;
//                    
//                         tmpUri = tmpUris.get( code + hash )  ;    
//                         
//                         /* Force Try with Code */ 
//                         
//                         if(tmpUri == null ) {
//                             
//                             final int code_finale = code ;
//                             
//                             tmpUri = uris_num.entrySet()
//                                              .stream()
//                                              .filter(e -> e.getValue() == code_finale )
//                                              .map(Map.Entry::getKey)
//                                              .findFirst()
//                                              .orElse(null).replaceFirst(":", "") ;
//                         }
//                    }
//                            
//                     if ( tmpUri == null ) return ;
//                     
//                     if(i == 0 )  { 
//                        uri = tmpUri     ;
//                        codeQuery = code ;
//                        value += tmpUri + " a " +  PREFIX_PREDICAT + ":" + type + " ; " ;
//                        if( ! root ) { 
//                            uris_parallel_root.add( ":" + uri ) ; 
//                            root = true ;
//                        } 
//                        
//                     }
//                     else {
//                         if( code == -1 )
//                            value += ( token.contains(":") || token.startsWith("?")) ? " " + token + " " : PREFIX_PREDICAT + ":" + token + " " ;
//                         else 
//                            value += ":" + tmpUri + " " ;
//                     }
//                }
//               
//                target_parallel.put( ":" + uri, value ) ;  
//                
//                if(codeQuery != -1 ) {
//                  uris_num_parallel.put( ":" + uri , codeQuery )                               ;
//                  if( source.get(hash + codeQuery) != null ){
//                     uris_queries_parallel.put(    ":" + uri , source.get(hash + codeQuery) )  ;
//                  }
//                  else {
//                     uris_queries_parallel.put(    ":" + uri , source.get(sourceByCode.get(codeQuery)) ) ; 
//                  }
//                  
//                  uris_queries_parallel_key.put(    ":" + uri , key ) ;
//                }
//                
//            }
//        }       
//    }
//    
//    private void write(  String outFile )  {
//                
//        for ( Edge edge : edges ) {
//
//            Concept sujet = concepts.get(edge.getSujet())      ;
//            Concept objet = concepts.get(edge.getObjet())      ;
//
//            if(sujet == null || objet == null ) continue ;
//            
//            String objectProperty =  edge.getPredicat().contains(":") ? edge.getPredicat() :
//                    
//            PREFIX_PREDICAT + ":" + edge.getPredicat() ;
//
//            if(!target.containsKey(tmpUris.get(sujet.getHash()))) {
//
//                if( objet.getLabel().startsWith("<")           ||
//                        objet.getLabel().startsWith("{")       ||
//                        objet.getLabel().startsWith("\"")      ||
//                        objet.getLabel().startsWith("?")       ||
//                        existPrefixStartWith(objet.getLabel()) ||
//                        (  objet.getLabel().startsWith(":")    &&
//                           !objet.getLabel().endsWith("::#")
//                        )
//                ) {
//                    
//                    if(!sujet.getType().startsWith(":") && !sujet.getType().startsWith("?"))  {
//
//                        if(objet.getLabel().startsWith("?") && objet.getCode() >  0 ) {
//                            
//                            target.put( tmpUris.get(sujet.getHash())  ,
//                                    tmpUris.get(sujet.getHash())           +
//                                            " a " +  PREFIX_PREDICAT + ":" +
//                                            sujet.getType() + " ; "    +
//                                            objectProperty  +  " :"         +
//                                            tmpUris.get(objet.getHash()) )             ;
//                        }
//                        
//                        else {
//                            
//                            target.put( tmpUris.get(sujet.getHash())  ,
//                                    tmpUris.get(sujet.getHash())           +
//                                            " a " +  PREFIX_PREDICAT + ":" +
//                                            sujet.getType() + " ; "    +
//                                            objectProperty  +  " "         +
//                                            objet.getLabel() )             ;
//                        }
//                    }
//                    
//                    else {
//
//                        target.put( tmpUris.get(sujet.getHash())   ,
//                                tmpUris.get(sujet.getHash())          +
//                                        " a " + sujet.getType()   +
//                                        " ; " + PREFIX_PREDICAT       +
//                                        ":"   + edge.getPredicat()    +
//                                        " "   + objet.getLabel() )    ;
//                    }
//
//                }
//
//                else {
//                     
//                        if( objet.getLabel().startsWith(MATCHER_PATTERN_PARALLEL )) {
//                            
//                             target.put( tmpUris.get(sujet.getHash()) ,
//                                        tmpUris.get(sujet.getHash())           +
//                                         " a " + PREFIX_PREDICAT + ":"  +
//                                         sujet.getType() + " ;  "   +
//                                         objectProperty      + " "      +
//                                         objet.getLabel() )                      ; 
//                        }
//                        
//                        else {
//                            
//                            String uri =  tmpUris.get(objet.getHash()) != null ?
//                                          ":" + tmpUris.get(objet.getHash()) : 
//                                               uris_num.entrySet()
//                                                       .stream()
//                                                       .filter(e -> e.getValue() == objet.getCode() )
//                                                       .map(Map.Entry::getKey)
//                                                       .findFirst()
//                                                       .orElse(null) ;
// 
//                            if( !sujet.getLabel().startsWith(MATCHER_PATTERN_CONTEXT)  &&
//                                     !sujet.getLabel().startsWith(MATCHER_PATTERN_PARALLEL )) {
//
//                                target.put( tmpUris.get(sujet.getHash()) ,
//                                            tmpUris.get(sujet.getHash())           +
//                                             " a " + PREFIX_PREDICAT + ":"  +
//                                             sujet.getType() + " ;  "   +
//                                             objectProperty      + " "      +
//                                             uri )                          ;                            
//                            }
//                            
//                            else {
//
//                                String targ = target.get(sujet.getLabel()) != null ? target.get(sujet.getLabel()) : " " ;
//                                target.put( sujet.getLabel() ,
//                                   targ + objectProperty  + " " + uri + " _+_ " ) ;
//                            }
//
//                            if( uri == null ) {
//                                System.err.println(" ")           ;
//                                System.err.println(" -------- " ) ;
//                                System.err.println(" Uri Not found for : " + objet.toString() ) ;
//                                System.err.println(" -------- ") ;
//                                System.err.println(" ")          ;
//                            }                    
//                        }
//                }
//
//               if(tmpUris.get( sujet.getHash()) != null)
//                    uris_queries.put( ":" + tmpUris.get( sujet.getHash() ) ,
//                                    source.get(sujet.getHash()))   ;
//            }
//            else {               
//                               
//                if ( objet.getLabel().startsWith("<")       ||
//                     objet.getLabel().startsWith("{")       ||
//                     objet.getLabel().startsWith("\"")      ||
//                     objet.getLabel().startsWith("?")       ||
//                     existPrefixStartWith(objet.getLabel()) ||
//                     (  objet.getLabel().startsWith(":")    &&
//                       !objet.getLabel().endsWith("::#") ))  {
//
//                    if(!target.get(
//                            tmpUris.get(sujet.getHash()))
//                            .contains( objectProperty
//                            + " " + objet.getLabel())
//                    )
//
//                        target.put( tmpUris.get(sujet.getHash()) ,
//                                    target.get(
//                                        tmpUris.get(sujet.getHash())) + " ; " +
//                                        objectProperty +  " "                 +
//                                        objet.getLabel() )                    ;
//                }
//                else {
//
//                    if ( !target.get(tmpUris.get(sujet.getHash()))
//                                .contains( objectProperty  + " :"
//                                + tmpUris.get(objet.getHash()) )
//                    )
//
//                    { 
//                        
//                       String uri =  "" ;
//                       
//                       if( objet.getLabel().startsWith(MATCHER_PATTERN_CONTEXT)  || 
//                           objet.getLabel().startsWith(MATCHER_PATTERN_PARALLEL) ||
//                           objet.getLabel().startsWith(MATCHER_VARIABLE) ) {
//                            
//                              target.put( tmpUris.get(sujet.getHash())      ,
//                                   target.get(tmpUris.get(sujet.getHash())) +
//                                   " ; "  +  objectProperty   +  " "        +
//                                   objet.getLabel() )       ;
//                              
//                       }
//                       else {
//                        
//                        uri =  tmpUris.get(objet.getHash()) != null ?
//                                 ":" + tmpUris.get(objet.getHash()) : 
//                                       uris_num.entrySet()
//                                              .stream()
//                                              .filter(e -> e.getValue() == objet.getCode() )
//                                              .map(Map.Entry::getKey)
//                                              .findFirst()
//                                              .orElse(null);
//
//                        target.put( tmpUris.get(sujet.getHash())             ,
//                                    target.get(tmpUris.get(sujet.getHash())) +
//                                    " ; "  +  objectProperty   +  " "        +
//                                    uri )                                    ;
//                      
//                       }
//                                               
//                        if( uri == null ) {
//                            System.err.println(" ") ;
//                            System.err.println("  Uri with code { "+ objet.getCode() + " }  not found ! ") ;
//                            System.err.println(" ") ;
//                        }
//
//                    }
//                }
//            }
//        }
//
//        for (Map.Entry<String, String> entrySet : target.entrySet()) {
//            
//            String key   = entrySet.getKey()    ;
//            String value = entrySet.getValue()  ;
//            
//            if( !key.startsWith(MATCHER_PATTERN_CONTEXT) )
//                target.put(key, ":" + value + " .") ;
//            else
//                target.put(key, value + " .") ;
//        }
//
//        List<String> outs    = new ArrayList<>() ;
//
//        if( !existHeader ) {
//
//            outs.add(PREFIXDECLARATION)  ;
//
//            for (Map.Entry<String, String> entrySet : prefix.entrySet()) {
//                String key   = entrySet.getKey()      ;
//                String uri   = entrySet.getValue()    ;
//                outs.add( PREF.replace("?pref", key)
//                              .replace("?uri", uri))  ;
//            }
//
//            outs.add("") ;
//
//            String SOURCE_DEC_STRING = "[SourceDeclaration]\n"              +
//                                        "sourceUri	?sourceUri\n"       +
//                                        "connectionUrl	?connectionUrl\n"   +
//                                        "username	?username\n"        +
//                                        "password	?password\n"        +
//                                        "driverClass	?driverClass"       ;
//
//            outs.add(SOURCE_DEC_STRING.replace("?sourceUri", SourceDeclaration.get("sourceUri"))
//                                      .replace("?connectionUrl", SourceDeclaration.get("connectionUrl"))
//                                      .replace("?username", SourceDeclaration.get("username"))
//                                      .replace("?password", SourceDeclaration.get("password"))
//                                      .replace("?driverClass", SourceDeclaration.get("driverClass"))
//            )  ;
//
//            outs.add("")                       ;
//            outs.add(MAPPING_COLLECTION_BEGIN) ;
//            outs.add("")                       ;
//
//            existHeader = true                 ;
//
//        }
//      
//        for (Map.Entry<String, String> entrySet : target.entrySet()) {
//
//            String key    = entrySet.getKey()     ;
//            String myTarget = entrySet.getValue() ;
//
//            int num         = -10 ;
//            String keyByURI = null ;
//            
//            if(!key.startsWith(MATCHER_PATTERN_CONTEXT)) {
//                
//                num = uris_num.get(myTarget.split(" ")[0]) ;
//                 
//                uris_num.get(myTarget.split(" ")[0]) ;
//
//                if ( myTarget.contains ( ":null" ) ) {
//                  System.err.println(" ") ;
//                  System.err.println("  Null Value # Something went wrong with code { " + num + " } ") ;
//                  System.err.println(" ") ;
//                } 
//
//                keyByURI = getKeyByURI("("+num+")_"+myTarget.split(" ")[0]) ;
//            
//            }
//            else {
//                 continue ;                
//            }
//            
//            if(keyByURI.endsWith("_") ) {
//                keyByURI = keyByURI.substring(0, keyByURI.length() - 1 ) ;
//            }
//            if ( ! myTarget.startsWith (":null") && ! myTarget.endsWith(" _+_  .") ) {
//
//                if ( uris_queries.get(myTarget.split(" ")[0] ) == null ) {
//                    try {
//                        throw new Exception(" No Query found for : " + myTarget.split(" ")[0] ) ;
//                    } catch (Exception ex) {
//                        Logger.getLogger(ProcessorCsv.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//
//                outs.add( MAPPING_COLLECTION_PATTERN.replace("?id", keyByURI )
//                                                    .replace("?target"  , myTarget)
//                                                    .replace("?source"  ,
//                          uris_queries.get(myTarget.split(" ")[0])).replace("  ", " " )
//                ) ;
//
//                outs.add("") ;
//            }
//            
//        }
//
//       if(!isGraphPattern ) {
//            try {
//                Writer.checkFile(outFile)           ;
//                Writer.writeTextFile(outs, outFile) ;
//                Writer.writeTextFile(Collections.singletonList(MAPPING_COLLECTION_END), outFile) ;
//            } catch (IOException ex) {
//                Logger.getLogger(ProcessorCsv.class.getName()).log(Level.SEVERE, null, ex);
//            }
//       }
//       
//       else  {           
//                               
//             List<String> copyOuts ;
//             
//             String _fileName = outFile.substring(0, outFile.lastIndexOf('.')) ;
//             
//             String extension = outFile.substring(outFile.lastIndexOf('.')) ;
//             
//             for(String vari : VARIABLES ) {
//                
//                String pattern_id = null , variable  ;
//
//                if( vari.trim()
//                        .split(Pattern.quote("&&"))[0]
//                        .replaceAll(" +", " ")
//                        .split(" ")[0]
//                        .startsWith(MATCHER_PATTERN_CONTEXT))  {
//                    
//                    pattern_id = vari.trim().replaceAll(" +", " ").split(" ")[0] ;
//                    variable   = vari.trim().replaceAll(" +", " ").split(" ")[1] ;
//                                     
//                }
//                else {
//                    variable   = vari.trim().replaceAll(" +", " ").split(" ")[0] ;
//                }
//                
//                Pattern p = Pattern.compile("\\{.*?\\}") ;
//                Matcher m = p.matcher(vari) ;
//
//                copyOuts = new ArrayList<>(outs) ;
//                
//                if(pattern_id != null ) {
//                  outs.addAll(getOutForPatternContext(pattern_id ));
//                }
//                else {
//                    target.get(MATCHER_PATTERN_CONTEXT);
//                }
//                
//                while (m.find()) {
//                     
//                    String param = m.group().replace("{", "")
//                                    .replace("}","").trim().replaceAll(" +", " ") ;
//                    
//                    String param_0 = param.split("=")[0]              ;
//                    String param_1 = param.split("=")[1]              ;
//                    outs.replaceAll( x -> x.replace(param_0,param_1)) ;
//                }
//               
//                if( linker != null ) {
//                    outs.replaceAll( x -> x.replace( MATCHER_PATTERN_CONTEXT , linker )
//                                           .replace( MATCHER_VARIABLE, variable  )
//                                           .replace( MATCHER_ENTITY  , variable )
//                    );
//                }
//                else {
//                    
//                    /* the ( ;.* ) is used to remove the object property of MATCHER_CONTEXT */
//                    
//                    outs.replaceAll( x -> x.replaceAll(";.* " + MATCHER_PATTERN_CONTEXT , target.get(MATCHER_PATTERN_CONTEXT) )
//                                           .replace("_+_  .", "") 
//                                           .replace(" _+_ ", " ; ")
//                                           .replace( MATCHER_VARIABLE, variable  )
//                                           .replace( MATCHER_ENTITY  , variable )
//                    ) ;
//                }
//                
//                /* PATTERN_PARALLEL */ 
//                
//                String[] patternParallel = null ;
//                
//                if(vari.contains("&&")) {                    
//                    patternParallel = vari.trim()
//                                          .split(Pattern.quote("&&"))[1]
//                                          .replaceAll(" +", " ")
//                                          .split(";") ;
//                }
//                                              
//                int i = 1 ;
//                 
//                String parallel_root_uris     =   String.join(" , ", uris_parallel_root) ;
//                
//                String parallel_root_uris_out =   "" ;
//                String parallel_root_uris_tmp =   "" ;
//                
//                if( patternParallel != null ) {
//                    
//                
//                    for( String  pattern_parallels : patternParallel ) {
//
//                        String pattern_key = pattern_parallels.trim().split(" ")[0] ;
//
//                        Matcher matcher    = p.matcher(pattern_parallels) ;
//
//                        parallel_root_uris_tmp = parallel_root_uris ;
//
//                        List<String> uris_parallel = uris_queries_parallel_key.entrySet()
//                                                                              .stream()
//                                                                              .filter(map -> map.getValue()
//                                                                              .equals(pattern_key))
//                                                                              .map( val -> val.getKey().replaceFirst(":", ""))
//                                                                              .collect(toList()) ;
//
//                        Map<String,  String>  kay_values  =  new  HashMap<>()   ;
//
//                        while (matcher.find()) {
//
//                            String param = matcher.group()
//                                                  .replace("{", "")
//                                                  .replace("}","")
//                                                  .trim().replaceAll(" +", " ") ;
//
//                            String key   = param.split("=")[0]  ;
//                            String value = param.split("=")[1]  ;
//
//                            kay_values.put(key, value)          ;
//
//                        }
//
//                        for( String uri : uris_parallel ) {
//
//                              String key_uri =  getKeyByURI ( "("+ uris_num_parallel.get(":"+uri)+")_"+variable ) ;
//                              String targ    =  target_parallel.get( ":" + uri ) ;
//                              String query   =  uris_queries_parallel.get(":" + uri ) ;
//
//                              if(query == null ) {
//                                 System.err.println(" ") ;
//                                 System.err.println("  SQL Query for Uri : { " +  uri + " }  not found ! // in uris_queries_parallel ") ;
//                                 System.err.println(" ") ;
//                              }
//                               
//                              key_uri = key_uri + "_" + i++  ;
//
//                              for (Map.Entry<String, String> entry : kay_values.entrySet()) {
//                                  String key   = entry.getKey()   ;
//                                  String value = entry.getValue() ;
//
//                                  targ               = targ.replace(  key , value )           ;
// 
//                                  query              = query.replace( key , value )           ;
//                                  parallel_root_uris_tmp = parallel_root_uris_tmp.replace(key, value ) ;
//
//                            } 
//
//                            outs.add( MAPPING_COLLECTION_PATTERN
//                                      .replace("?id", MATCHER_PATTERN_PARALLEL.replace("##", "") + "_" + key_uri )
//                                      .replace("?target"  , ":" + targ + "." )
//                                      .replace("?source"  , query )
//                            ) ;
//
//                            outs.add( "");
//
//                        }
//
//                        parallel_root_uris_out = String.join(" , ", parallel_root_uris_out, parallel_root_uris_tmp ) ;
//                    }
//                }
//                
//                String final_root_uris =  parallel_root_uris_out.replaceFirst(", ", "").trim() ;
//
//                outs.replaceAll( x -> x.replaceAll(MATCHER_PATTERN_PARALLEL, final_root_uris )) ;
//                
//                String fileName =  _fileName + "_" + variable.replaceFirst(":", "") + extension ;
//                
//                
//                 try {                
//                    Writer.checkFile( fileName )  ;
//                    Writer.writeTextFile(outs, fileName ) ;
//                    Writer.writeTextFile(Collections.singletonList(MAPPING_COLLECTION_END), fileName ) ;
//                 } catch (IOException ex) {
//                     Logger.getLogger(ProcessorCsv.class.getName()).log(Level.SEVERE, null, ex);
//                 }
//                
//                outs = new ArrayList<>(copyOuts) ;
//                
//             }  
//       }
//        
//    }
//
//    private List<String> getOutForPatternContext( String patternId ) {
//      
//        List<String> out      = new ArrayList<>() ;
//        
//        linker                = null              ;
//        
//        int num_start         = Integer.parseInt(PATTERNS_CONTEXT.get(patternId).split(" ")[0]) ;
//                  
//        String URI_PATTERN    = PATTERNS_CONTEXT.get(patternId).split(" ")[1]  ;
//                  
//        String objectProperty =  PATTERNS_CONTEXT.get(patternId).split(" ")[2] ;
//                  
//        String  keyByURI      = getKeyByURI("("+ patternId.replace("##", "") +")") ;
//        
//        Pattern p             = Pattern.compile("\\[.*?\\]") ;
//        
//        Matcher m             = p.matcher(PATTERNS_CONTEXT.get(patternId)) ;
//       
//        List<String> vars = new ArrayList();
//        
//        while (m.find()) {
//        
//            vars.add( m.group().replace("[", "").replace("]","").trim() ) ;
//        
//        }
//            
//        for (int i = 0; i < vars.size(); i++) {
//
//            String entity = vars.get(i).split(" " )[0]  ;
//            String type   = entity.split("_")[0]        ;
//            String classe = entity.split("_")[1]        ;
//
//            int numQuery = Integer.parseInt(vars.get(i)
//                                  .split(" " )[1].split("_")[1]) ;
//                
//            String query = source.get(sourceByCode.get(numQuery)) ;
//                
//            if( query == null ) {
//                System.out.println("")   ;
//                System.out.println(" NumQuery [ "+numQuery+" ] not found in numUris Map !! " ) ;
//                System.out.println("")   ;
//            }
//            
//            String uri = URI_PATTERN.replace(MATCHER_ENTITY , classe.toLowerCase(Locale.FRENCH) ) ;
//
//            if(! uri.startsWith(Pattern.quote(":"))) uri = ":" + uri  ;
//
//            if(i == vars.size() -1 ) {
//
//                  out.add( MAPPING_COLLECTION_PATTERN
//                          .replace("?id", keyByURI+"_"+classe+ "_"+num_start++ )
//                          .replace("?target"  ,  uri + " a " + type + " ; " +
//                           OF_ENTITY_PATTERN + " :" + classe + " ; " + 
//                           target.getOrDefault(MATCHER_PATTERN_CONTEXT, "")
//                                 .replace("_+_  .", ".") )
//                                 .replace(" _+_ ", " ; ")
//                                 .replace("?source"  , query )
//                  ) ;
//            }
//
//            else {
//
//              String nextEntityClass = vars.get(i+1).split(" ")[0].split("_")[1] ;
//              String nextUri = URI_PATTERN.replace( MATCHER_ENTITY , nextEntityClass.toLowerCase() ) ;
//
//              if( ! nextUri.startsWith(Pattern.quote(":")))
//                  nextUri = " :" + URI_PATTERN.replace( MATCHER_ENTITY , nextEntityClass.toLowerCase() ) ;
//              else
//                  nextUri = " " + URI_PATTERN.replace( MATCHER_ENTITY , nextEntityClass.toLowerCase() ) ;
//
//              out.add( MAPPING_COLLECTION_PATTERN
//                       .replace("?id", keyByURI+"_"+classe+ "_"+num_start++ )
//                       .replace("?target"  , uri + " a " + type + " ; " +
//                        OF_ENTITY_PATTERN + " :" + classe + " ; " + objectProperty + nextUri + " ." )
//                       .replace("?source"  , query )
//              ) ;
//
//            }
//            
//            if(i == 0 ) {
//               linker = uri ;
//            }
//
//            out.add("") ;
//        }
//               
//        return out ;
//    }
//    
//    
//    private String getKeyByURI(String target) {
//        String code =  target.replaceAll(Pattern.quote("/{"), "_")
//                             .replaceAll(Pattern.quote("-{"), "_")
//                             .replaceAll(Pattern.quote("/"), "_" )
//                             .replaceAll(Pattern.quote("{"), "_" )
//                             .replaceAll(Pattern.quote("}"), "_" )
//                             .replaceAll(Pattern.quote(":"), "_" )
//                             .replaceAll("_+", "_")              
//                             .replaceAll("##", "")              ;
//                             
//        if(code.startsWith("_")) return code.substring(1, code.length()) ;
//        return code ;
//    }
//
//    private int getHash( String pathFile ) {
//       Objects.requireNonNull(pathFile) ;
//       return  pathFile.hashCode()      ;
//    }
//
//    private void process( String pathFile ) throws IOException {
//
//        JSONObject jsonObject = loadJsonObject(pathFile) ;
//        int        hash       = getHash(pathFile)        ;
//        loadConcepts (jsonObject, hash)                     ;
//        loadEdges (jsonObject, hash )                    ;
//        treatParallelPatterns( hash )                    ;
//        
//    }
//
//
//    public void entryProcess( String directory       ,
//                              String outObdaPathFile ,
//                              String extensionFile   ,
//                              String csv             ) throws Exception {
//
//        existHeader       = false  ;
//
//        boolean processed = false  ;
//
//        List<Path> files = Files.list(new File(directory).toPath()).collect(toList()) ;
//
//        for(Path path : files ) {
//            if(path.toString().endsWith(extensionFile )) {
//                process(path.toString() ) ;
//                if ( ! processed ) processed = true ;
//            }
//        }
//
//        
//        if( processed ) {
//
//            Files.lines(Paths.get(csv)).skip(1).forEach ( 
//                  line ->  processCSVPatterns(line , outObdaPathFile )
//            ) ;
//            
//        }
//        else {
//            System.out.println ( " No File with extension '" +extensionFile + "' found !! " ) ;
//            System.out.println ( "                                                        " ) ;
//        }
//
//    }
//
//    private String processCSVPatterns(String line , String outObdaPathFile ) {
//         
//        isGraphPattern = true     ;
//        
//        target.clear()            ;
//        VARIABLES.clear()         ;
//        PATTERNS_CONTEXT.clear()  ;
//        PATTERNS_PARALLEL.clear() ;
//        
//        uris_num_parallel.clear()         ;
//        uris_queries_parallel.clear()     ;
//        uris_queries_parallel_key.clear() ;
//                  
//        String patternContext  = generatePatternContext(line)  ;
//        String patternParallel = generatePatternParallel(line) ;
//        String variable        = generateVariable(line)        ;
//        
//        
//        if(patternContext == null) {
//          variable  = variable.replaceFirst(MATCHER_PATTERN_CONTEXT, "").trim() ;
//        }
//        
//        PATTERNS_CONTEXT.put(MATCHER_PATTERN_CONTEXT, patternContext)         ;
//        
//        PATTERNS_PARALLEL.put(MATCHER_PATTERN_PARALLEL, patternParallel) ;
//        VARIABLES.add( variable) ;
//        
//        treatParallelPatterns( getHash(outObdaPathFile)) ;
//        write(outObdaPathFile) ;
//        
//        return line ;
//    }
//    
//    
//    private String generatePatternContext (String line ) {
//
//        String pattern = "" ;
//        
//        String metaPatternContext = meta_paterns.get(META_PATTERN_CONTEXT)
//                                                .trim() ;
//        String base  =  metaPatternContext.split(Pattern.quote("["))[0] ;
//        String matcher = metaPatternContext.split(Pattern.quote("["))[1]
//                                           .replace("]", "")
//                                           .trim() ;
//        
//        int variablesColumnNum = Integer.parseInt( matcher.split(" ")[0].split("_COLUMN_")[1]) ;
//        
//        String nums            = matcher.split("Q_")[1] ;
//        int startQueryNum      = Integer.parseInt(nums.split("_")[0])         ;
//        int middleQueryNum     = Integer.parseInt(nums.split("_")[1])         ;
//        int endQueryNum        = Integer.parseInt(nums.split("_")[2])         ;
//        
//        int loop                  = startQueryNum ;
//        
//        if(line.split(SEPARATOR)[variablesColumnNum].trim().length() == 0 ) return null ;
//        
//        String[] variablesContext = line.split(SEPARATOR)[variablesColumnNum].replaceAll(" +", "").trim().split(",") ;
//       
//        Collections.reverse(Arrays.asList(variablesContext)) ;
//
//        if( variablesContext.length == 1 ) loop += 2 ;
//        
//        for (int i = 0 ; i < variablesContext.length; i++ ) {
//            
//            String variable = variablesContext[i] ;
//            
//            pattern += "[ " + matcher.replace("COLUMN_" + variablesColumnNum , variable )
//                                     .replace(" Q_" + nums, " Q_" + String.valueOf(loop) ) + " ] " ;
//            
//            if( i == variablesContext.length - 2 || variablesContext.length ==  2 ) {
//                loop = endQueryNum ;
//            }
//            else 
//            if( i == 0 ) {
//                loop = middleQueryNum ;
//            }
//        }
//        
//        return base + pattern ;
//        
//    }
//    
//    private String generatePatternParallel (String line  ) {
//        
//        return meta_paterns.get(META_PATTERN_PARALLEL) ;
//    }
//
//    
//    private String generateVariable (String line  ) {
//        
//         
//         String metaPatternVariable = meta_paterns.get(META_VERIABLE) ;
//        
//         String variable = metaPatternVariable ;
//         
//         Pattern p = Pattern.compile("COLUMN_+\\w+") ;
//         Matcher m = p.matcher(metaPatternVariable ) ;
//
//         while (m.find()) {
//                     
//            String params = m.group().replace(" + ", "").trim() ;
//            String[] nums  = params.split("_");
//            
//            List<String> tmp = new ArrayList<>();
//            
//            for( int i = 1 ; i < nums.length ; i ++ ) {
//                
//                int num = Integer.parseInt(nums[i])       ;
//                tmp.add(line.split(SEPARATOR)[num])       ;
//            }
//
//            variable = variable.replaceAll(params, String.join(":",tmp)) ;
//         }
//               
//                
//         return variable.replace(META_PATTERN_CONTEXT, MATCHER_PATTERN_CONTEXT)
//                        .replace(META_PATTERN_PARALLEL, MATCHER_PATTERN_PARALLEL);
//    }
//    
//  
}
