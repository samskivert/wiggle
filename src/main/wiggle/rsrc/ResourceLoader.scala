//
// $Id$

package wiggle.rsrc

import java.io.File

/**
 * Handles the loading of data from the classpath or filesystem.
 */
class ResourceLoader (providers :Function1[String, Option[Resource]]*)
  extends Function1[String, Resource]
{
  /** Locates and returns the resource with the specified path.
   * @throws NoSuchElementException if the resource could not be found.
   */
  def get (path :String) = getOption(path) match {
    case Some(resource) => resource
    case None => throw new NoSuchElementException("Could not find resource '" + path + "'")
  }

  /** Locates and returns an option on the specified resource. */
  def getOption (path :String) = providers.flatMap(_(path)).firstOption

  /** Treats the resource loader as a function from path to Resource.
   * @see #get */
  def apply (path :String) = get(path)
}

/**
 * Defines standard resource loading functions which can be composed into a loader.
 */
object ResourceLoader
{
  /** Returns a loader that obtains resources from the supplied classloader. */
  def viaClassPath (loader :ClassLoader) = (path :String) => {
    val url = loader.getResource(path)
    if (url == null) None else Some(Resource.urlResource(url))
  }

  /** Returns a loader that obtains resources from the specified directory. */
  def viaFileSystem (root :String) = (path :String) => {
    val file = new File(new File(root), path)
    if (file.exists) Some(Resource.fileResource(file)) else None
  }
}

package wiggle.rsrc.tests {
  import org.scalatest.Suite

  class ResourceLoaderSuite extends Suite {
    def testViaFileSystem () {
      val root = System.getProperty("user.dir")
      val fpath = "rsrc/test.txt"
      val floader = new ResourceLoader(ResourceLoader.viaFileSystem(root))
      val frsrc = floader.get(fpath)
      println(frsrc.asFile match {
        case None => fail("FileSystem loader returned stream for " + fpath)
        case Some(file) => expect(new File(root, fpath).getPath) { file.getPath }
      })
    }

    def testViaClassPath () {
      val cppath = "test.txt"
      val cploader = new ResourceLoader(ResourceLoader.viaClassPath(getClass.getClassLoader))
      val cprsrc = cploader.get(cppath)
      println(cprsrc.asFile match {
        case None => assert(cprsrc.asStream != null)
        case Some(file) => fail("ClassPath loader returned stream for " + cppath)
      })
    }
  }
}