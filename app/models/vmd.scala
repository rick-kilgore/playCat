package models

import org.joda.time.DateTime
import com.novus.salat.dao.SalatDAO
import org.bson.types.ObjectId
import com.mongodb.casbah.commons.Imports._
import com.novus.salat._
import play.Play
import mongoContext.context
import com.mongodb.casbah.commons.MongoDBObject
import com.novus.salat.annotations._

case class VMD(fileurl: String,
               vidtype: String,
               title: String,
               writers: List[String],
               director: String,
               producers: List[String],
               cast: List[String],
               year: Int,
               genre: String,
               encoding: String,
               captions: Boolean,
               duration: Int,  // minutes
               resolution: List[Int],
               description: String,
               creationDate: Option[DateTime],
               modDate: Option[DateTime],
               @Key("_id") id: ObjectId = new ObjectId)

/*
object VMD {
    def save(vmd: VMD) {
        VMDDao.insert(vmd)
    }
}
*/

object VMDDao extends SalatDAO[VMD, ObjectId](collection = DB.connection("videoMetadata")) {

    def findAll(limit: Int): List[VMD] = {
        val movies = VMDDao.find(MongoDBObject()).sort(orderBy = MongoDBObject("fileurl" -> 1)).limit(limit)
        movies.toList
    }

    def findById(id: String): VMD = {
        val movies = VMDDao.find(MongoDBObject("_id" -> new ObjectId(id)))
        // assert(movies.size == 1)
        movies.toList.head
    }

    def findByName(url: String): List[VMD] = {
        val movies = VMDDao.find(MongoDBObject("fileurl" -> url.r)).sort(orderBy = MongoDBObject("fileurl" -> 1))
        movies.toList
    }
};

