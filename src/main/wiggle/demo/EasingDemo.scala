//
// $Id$
//
// Copyright (C) 2008-2009 Michael Bayne
//
// Licensed under the Apache License, Version 2.0 (the "License"); you may not use
// this file except in compliance with the License. You may obtain a copy of the
// License at: http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software distributed
// under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
// CONDITIONS OF ANY KIND, either express or implied. See the License for the
// specific language governing permissions and limitations under the License.

package wiggle.demo

import org.lwjgl.opengl.GL11

import wiggle.app.{App, DisplayConfig, Entity}
import wiggle.gfx.{Color, Element, Group, Image, Primitive}
import wiggle.rsrc.{PixelsLoader, PixelsKey, ResourceLoader, TextureCache}
import wiggle.util.{Interpolator, Task, Taskable}

/**
 * A demonstration of the easing functions.
 */
object EasingDemo
{
  def main (args :Array[String]) {
    val config = DisplayConfig("Easing Demo", 60, 800, 600)
    var app :App = new App(config)
    app.init()

    val group = new Group with Entity
    var idx = 0
    for (y <- 600.to(0).by(-100); x <- 0.to(800).by(100); if (y != 300)) {
      var square = makeSquare
      square.move(x, -100)
      group.add(square)
      group.add(square.yM.delay((600-y)/600f + 0.5f*(x/600f)).easeIn(y, 1))
      group.add(square.orientM.delay(2.5f).easeInOut(90, 1))
      idx = idx + 1
    }
    app.add(group)

    val mover = new Group with Entity
    mover.add(makeSquare)
    mover.move(0, config.height/2)
    mover.orientM.linear(360, 2).set(0).repeat.bind(mover)
    mover.xM.easeInOut(config.width/2, 1).delay(1).easeInOut(config.width, 1).
      easeIn(config.width/2, 1).easeOut(0, 1).repeat.bind(mover)
    app.add(mover)

    app.run()
  }

  def makeSquare = Primitive.makeColorVertex(4).
    color(Color.Blue).vertex(-50, -50).
    color(Color.White).vertex(50, -50).
    color(Color.Blue).vertex(50, 50).
    color(Color.White).vertex(-50, 50).buildQuads
}
