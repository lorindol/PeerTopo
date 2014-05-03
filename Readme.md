Peer Topo
=========

Free the topos.
Display. Search. Share.

Setup
=====

Install Android Studio from http://developer.android.com
(Building requires Gradle)
Import App
Run

About
=====
PeerTopo enables you to display ready-made topos. A topo-file is a zipfile that has an extension of .topo
Every topo at least contains one route.xml which references at least one image file (gif, png, ...)
A route file might look like


 \<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

 \<topo version="1.0">
    \<description>description of the topo\</description>
    \<image>\<name>topo.gif\</name>\</image>

    <route>
        <index>number - reference to the image content</index>
        <name>name of the route</name>
        <difficulty>difficulty in UIAA scale</difficulty>
    </route>
    ...
 \</topo>