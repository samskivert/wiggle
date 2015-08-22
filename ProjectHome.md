## What is Wiggle? ##

Wiggle is a game development library/framework for Scala that uses LWJGL and hence OpenGL and OpenAL for visualization and noise making.

It aspires to contain the following components (and probably more as I undertake ever more sophisticated experiments):

  * resource loading and management (image and sound data, textures) (in progress)
  * 2D scene graph (vector, image and text rendering) (in progress)
  * entities and tasks for simulation and animation (in progress)
  * particle effect system (not started)
  * sound effect and music playback (not started)

Some day Wiggle will be useful for actual game development, but presently its main purpose in life is to allow me to experiment with new and/or more succinct ways to express the myriad APIs that go into game development.

I hope with Wiggle to achieve the same "scalability" (simple things are simple and succinct, powerful things are possible and not much more complex) that Scala has enabled in other programming domains.

## Building ##

You need three things:

  1. Scala - http://scala-lang.org/
  1. SBT - http://code.google.com/p/simple-build-tool/
  1. LWJGL - http://lwjgl.org/

Until LWJGL is published to the central Maven repositories, you need to
manually copy lwjgl.jar, lwjgl\_util.jar and the native/ directory into lib/
before building Wiggle. This should look like:

```
lib/lwjgl.jar
lib/lwjgl_util.jar
lib/native/linux/...
lib/native/macosx/...
lib/native/win32/...
```

To build and run demos, invoke:

`% sbt run`

Building has been tested on Linux and Mac OS X and hopefully works on Windows
as well.