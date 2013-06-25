package models

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.commons.conversions.scala._


object DB {
    RegisterJodaTimeConversionHelpers()
    val mc = MongoConnection("ec2-54-218-74-83.us-west-2.compute.amazonaws.com")
    println("connection = "+mc.getAddress())
    val connection = mc("test")
}

