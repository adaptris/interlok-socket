# interlok-socket [![GitHub tag](https://img.shields.io/github/tag/adaptris/interlok-socket.svg)](https://github.com/adaptris/interlok-socket/tags) [![Build Status](https://travis-ci.org/adaptris/interlok-socket.svg?branch=develop)](https://travis-ci.org/adaptris/interlok-socket) [![codecov](https://codecov.io/gh/adaptris/interlok-socket/branch/develop/graph/badge.svg)](https://codecov.io/gh/adaptris/interlok-socket) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/0488ef500fc240a3bd5a8842948fb993)](https://www.codacy.com/app/adaptris/interlok-socket?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=adaptris/interlok-socket&amp;utm_campaign=Badge_Grade)
The suggested projectname was literate-waffle...

As part of the 3.7.0 release, we decided to the "roll your own protocol that works over TCP sockets" into its own package. It has a very limited use-case and doesn't really deserve to live in Interlok proper.

So here it is, if you were looking for it, then you can still depend on it via gradle/ivy/maven as usual.

```
compile ("com.adaptris:interlok-socket:3.7-SNAPSHOT") { changing= true}
```

```
<dependency org="com.adaptris" name="interlok-socket" rev="3.7-SNAPSHOT" conf="runtime->default" changing="true"/>
```

```
<dependency>
  <groupId>com.adaptris</groupId>
  <artifactId>interlok-socket</artifactId>
  <version>3.7-SNAPSHOT</version>
</dependency>
```
