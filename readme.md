# Welcome to Cache Control 

  - **Author:** Marcio Romualdo da Silva
  - **Email:** marcioromualdo@yahoo.com.br
  - **Version:** 1.0

Taglib that controls the cache of .js and .css files.

The cache controllers (navigator web or proxy web) create cache of files (.js, .css, .gif, etc) to decrease the traffic network and improve perfomance access. 
When a file is changed at the web server, the user often doesn't receive the updated file because the cache controllers don't always verify if the file was changed at the server.

The objective of taglib is to send to the web navigator, the path of the resource (.js or .css) with a key word (/cache-control/) and the hash base 64 of the file.
    So, in this way, when the file is changed at the server, the navigator receives a new path of the resource because the hash of the file will change, and therefore the user will receive the updated version of the file.

**Performance:** The hash base64 of the files will be created on start up of server, and the server will keep in memory a list of created hashes.

**Key word:** the key word (/cache-control/) will be used as a filter to identify that a uri was encoded by the cache-control taglib. The key word and the hash base 64 is removed from the uri and is then forwarded to the real path uri.  

**Instructions:**
1.  put the cacheControl.jar in the WEB-INF/lib directory or in the web application classpath.      
2.  config the web.xml:
```
    <filter>
        <filter-name>CacheControlFilter</filter-name>
        <filter-class>com.cache.control.CacheControlFilter</filter-class>
        <init-param>
            <param-name>path_css</param-name>
            <param-value>/css</param-value>
            <description>Paths that indicate the location of the css files. If you indicate more than one path, separate them with a comma.</description>
        </init-param>
        <init-param>
            <param-name>path_js</param-name>
            <param-value>/js</param-value>
            <description>Paths that indicate the location of the js files. If you indicate more than one path, separate them with a comma.</description>
        </init-param>
     </filter>
     <filter-mapping>
        <filter-name>CacheControlFilter</filter-name>
        <url-pattern>/cache_control/*</url-pattern>
    </filter-mapping>
```

3.  use the taglib in .jsp:
```
    put: <%@ taglib prefix="cache-control" uri="cache-control-tags" %>
```
    examples:
    - tag js:
```
    Replace the tag html:
        from: <script language='javascript' src="/jscript/example1.js"/></script>
        to:   <cache-control:js language='javascript' src="/jscript/example1.js"/>
              or just: <cache-control:js src="/jscript/example1.js"/>
    
        Attributes:
            - the attribute "src" is required
            - the attribute "language" is optional, default: javascript
            - the attribute "charset" is optional, default: UTF-8
``` 
    - tag css:
```
    Replace the tag html:
        from: <link rel="stylesheet" type="text/css" href="/css/example2.css" media="all"/>
        to:   <cache-control:css rel="stylesheet" type="text/css" href="/css/example2.css" media="all"/>
              or just:
              <cache-control:css href="/css/example2.css"/>
    
        Attributes:
            - the attribute "src" is required
            - the attribute "rel" is optional, default: stylesheet
            - the attribute "type" is optional, default: text/css
            - the attribute "media" is optional, default: all
```
