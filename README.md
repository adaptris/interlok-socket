# interlok-socket 

[![GitHub tag](https://img.shields.io/github/tag/adaptris/interlok-socket.svg)](https://github.com/adaptris/interlok-socket/tags) [![Build Status](https://travis-ci.org/adaptris/interlok-socket.svg?branch=develop)](https://travis-ci.org/adaptris/interlok-socket) [![CircleCI](https://circleci.com/gh/adaptris/interlok-socket/tree/develop.svg?style=svg)](https://circleci.com/gh/adaptris/interlok-socket/tree/develop) [![codecov](https://codecov.io/gh/adaptris/interlok-socket/branch/develop/graph/badge.svg)](https://codecov.io/gh/adaptris/interlok-socket) [![Total alerts](https://img.shields.io/lgtm/alerts/g/adaptris/interlok-socket.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/adaptris/interlok-socket/alerts/) [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/adaptris/interlok-socket.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/adaptris/interlok-socket/context:java)

The suggested projectname was literate-waffle...

As part of the 3.7.0 release, we decided to the "roll your own protocol that works over TCP sockets" into its own package. It has a very limited use-case and doesn't really deserve to live in Interlok proper.

So here it is, if you were looking for it, then you can still depend on it via gradle/ivy/maven as usual.

```
compile ("com.adaptris:interlok-socket:3.8-SNAPSHOT") { changing= true}
```

```
<dependency org="com.adaptris" name="interlok-socket" rev="3.8-SNAPSHOT" conf="runtime->default" changing="true"/>
```

```
<dependency>
  <groupId>com.adaptris</groupId>
  <artifactId>interlok-socket</artifactId>
  <version>3.8-SNAPSHOT</version>
</dependency>
```
