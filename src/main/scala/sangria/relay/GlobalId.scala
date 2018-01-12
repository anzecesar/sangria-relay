package sangria.relay

import sangria.schema.{IDType, ScalarAlias}
import sangria.validation.{ValueCoercionViolation, Violation}

case class GlobalId(typeName: String, id: String) {
  def asString = GlobalId.toGlobalId(this)
}

object GlobalId {
  def toGlobalId(globalId: GlobalId): String = toGlobalId(globalId.typeName, globalId.id)

  /**
   * Takes a type name and an ID specific to that type name, and returns a
   * "global ID" that is unique among all types.
   */
  def toGlobalId(typeName: String, id: String): String = s"$typeName:$id"

  /**
   * Takes the "global ID" created by toGlobalID, and returns the type name and ID
   * used to create it.
   */
  def fromGlobalId(globalId: String) = globalId.split(":").toList match {
    case typeName :: id :: Nil => Some(GlobalId(typeName, id))
    case _                     => None
  }

  def unapply(globalId: String) = fromGlobalId(globalId)

  case object IdViolation extends ValueCoercionViolation("Invalid ID")

  /**
   * Creates a type alias to support using GlobalIDs across Relay applications
   */
  implicit val GlobalIdTypeAlias: ScalarAlias[GlobalId, String] = ScalarAlias[GlobalId, String](IDType,
    toScalar = _.asString,
    fromScalar = id ⇒ GlobalId.fromGlobalId(id).fold[Either[Violation, GlobalId]](Left(IdViolation))(Right(_)))
}
