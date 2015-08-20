package skuber.model

import coretypes._

import java.util.Date

/**
 * @author David O'Riordan
 */
case class ReplicationController(
  	val kind: String ="ReplicationController",
  	override val apiVersion: String = "v1",
    val metadata: ObjectMeta = ObjectMeta(),
    spec: Option[ReplicationController.Spec] = None,
    status: Option[ReplicationController.Status] = None) 
      extends ObjectResource with KListItem {
    def withReplicas(n: Int) = {  this.copy(spec = Some(spec.getOrElse(new ReplicationController.Spec(replicas = n))))}
    def withSelector(s: Map[String, String]) = { this.copy(spec = Some(spec.getOrElse(new ReplicationController.Spec(1)).copy(selector = Some(s)))) }    
    def withPodTemplate(t: Pod.Template.Spec) = { this.copy(spec = Some(spec.getOrElse(new ReplicationController.Spec(1)).copy(template = Some(t)))) }  
    def withPodSpec(t: Pod.Spec) = {
      val template = new Pod.Template.Spec(metadata=Some(ObjectMeta(this.metadata.name)),spec=Some(t))
      withPodTemplate(template)
    }
}

object ReplicationController {
  
    def named(name: String) = ReplicationController(metadata=ObjectMeta(name=name))
    def apply(name: String, spec: ReplicationController.Spec) : ReplicationController = 
                          ReplicationController(metadata=ObjectMeta(name=name), spec = Some(spec))
    case class Spec(
      replicas: Int=1,
      selector: Option[Map[String, String]] = None,
      template: Option[Pod.Template.Spec] = None) 
      
  case class Status(
      replicas: Int,
      observerdGeneration: Option[Int])
}