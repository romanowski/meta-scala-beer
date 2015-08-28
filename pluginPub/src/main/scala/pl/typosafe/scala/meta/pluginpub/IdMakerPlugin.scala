package pl.typosafe.scala.meta.pluginpub

import scala.tools.nsc
import nsc.Global
import nsc.Phase
import nsc.plugins.Plugin
import nsc.plugins.PluginComponent


class IdMakerPlugin(val global: Global) extends Plugin {

  import global._

  override val name = "idmaking"
  override val components: List[PluginComponent] = List(IdMakerComponent)
  override val description: String = "using id from passports"

  private object IdMakerComponent extends PluginComponent {
    override val global: Global = IdMakerPlugin.this.global

    class PassportTransformer extends Transformer{
      override def transform(tree: IdMakerPlugin.this.global.Tree): IdMakerPlugin.this.global.Tree = tree match{
        case clazz @ClassDef(mods, name, tparams, impl) if name.toString == "HasPassport" =>
          val newAgeFromIdMethod = q"override def ageFromId: Int = ageFromPassport"
          val hasIdTrait = tq"pl.typosafe.scala.meta.customer.HasId"

          val newClassImpl = Template(impl.parents :+ hasIdTrait, impl.self, newAgeFromIdMethod :: impl.body)
          ClassDef(mods, name, tparams, newClassImpl)
        case _ => super.transform(tree)
      }
    }


    override def newPhase(prev: Phase): Phase = new GlobalPhase(prev) {
      override def apply(unit: CompilationUnit): Unit = {
        //let's play with unit.body...
        println(s"Processing: $unit")
        unit.body = new PassportTransformer().transform(unit.body)
      }

      override def name: String = "idmakingphase"
    }

    override val runsAfter: List[String] = List("parser")
    override val phaseName: String = "idmakingphase"
  }


}
