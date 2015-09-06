package com.sksamuel.elastic4s

import org.elasticsearch.script.Script
import org.elasticsearch.script.ScriptService.{ScriptType => ESScriptType}

trait ScriptDsl {
  def script(name: String, script: String) = ScriptDefinition(name, script)
}

case class ScriptDefinition(name: String,
                            script: String,
                            lang: Option[String] = None,
                            scriptType: ESScriptType = ESScriptType.INLINE,
                            params: Map[String, AnyRef] = Map.empty) {

  import scala.collection.JavaConverters._

  def lang(lang: String): ScriptDefinition = copy(lang = Option(lang))
  def param(name: String, value: AnyRef): ScriptDefinition = copy(params = params + (name -> value))
  def params(map: Map[String, AnyRef]): ScriptDefinition = copy(params = params ++ map)
  def scriptType(scriptType: ESScriptType): ScriptDefinition = copy(scriptType = scriptType)

  def toJavaAPI: Script = new Script(script, scriptType, lang.orNull, params.asJava)
}
