# Welcome to Cache Control 

  - **Author:** Marcio Romualdo da Silva
  - **Email:** marcioromualdo@yahoo.com.br
  - **Version:** 1.1.0

Taglib that controls the cache of .js and .css files.

The cache controllers (navigator web or proxy web) create cache of files (.js, .css, .gif, etc) to decrease the traffic network and improve perfomance access. 
When a file is changed at the web server, the user often doesn't receive the updated file because the cache controllers don't always verify if the file was changed at the server.

The objective of this taglib is to send to the web navigator the path of the resource (.js or .css) with a parameter (cache-id) equals to the hash base 64 of the file. Example: https:.../file.js?cache-id=Lxb+4PNgIm0GGMgz40jTNA==

So, in this way, when the file is changed at the server, the navigator receives a new path of the resource because the hash of the file will change, and therefore the user will receive the updated version of the file.

**Performance:** The hash base64 of the files will be created only on the first call of the file and the server will keep in memory a list of created hashes.

**Instructions:**

**1.**  put the cacheControl-1.1.0.jar in the WEB-INF/lib directory or in the web application classpath.      

**2.**  use the taglib in .jsp:
```
    put: <%@ taglib prefix="cache-control" uri="cache-control-tags" %>
```
    - tag js:
```
    Replace the tag html:
        from: <script type='text/javascript' src="/jscript/example1.js"/></script>
        to:   <cache-control:js type='text/javascript' src="/jscript/example1.js"/>
              or just: <cache-control:js src="/jscript/example1.js"/>
    
        Attributes:
            - the attribute "src" is required
            - the attribute "type" is optional, default: text/javascript
            - the attribute "charset" is optional
            - the attribute "defer" is optional, default: false
            - the attribute "async" is optional, default: false
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
