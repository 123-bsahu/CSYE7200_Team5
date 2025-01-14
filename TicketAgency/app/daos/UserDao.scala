package daos

import com.mongodb.reactivestreams.client.{FindPublisher, MongoCollection}
import org.bson.Document
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Filters
import pojos._


object UserDao extends DbConfig {

    private val codecRegistry = fromRegistries(fromProviders(classOf[Ticket]), fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)

    val usersCollection: MongoCollection[User] = db.getCollection("users", classOf[User]).withCodecRegistry(codecRegistry)

    def findAll: FindPublisher[User] = usersCollection.find()

    def findByLogin(name: String, password: String): FindPublisher[User] =
        usersCollection.find(
            Filters.and(
                Filters.eq("name", name),
                Filters.eq("password", password)
            )
        )

    def findByName(name: String): FindPublisher[User] =
        usersCollection.find(Filters.eq("name", name))

}
