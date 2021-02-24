package scala

object HelloWold {
  def main(args: Array[String]): Unit = {
    var entityType = 0;

    for( entityType <- 0 to 1){
      println(entityType)

     var sql =  s"""
      | temp.entity_type = $entityType
      """

      println(sql)
    }
  }
}
