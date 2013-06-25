package controllers

import play.api._
import play.api.mvc._
import models.VMD
import models.VMDDao
import org.joda.time.DateTime

object Application extends Controller {

    def index = Action {
        Ok(views.html.index("playCat is ready."))
    }

    def echo = Action { request =>
        Ok("Received request [" + request + "]")
    }

    def hello(name: String) = Action {
        Ok("Hello "+name)
    }

    def save(fileName: String, title: String) = Action { implicit request =>
        println("save request: "+request.uri)
        val now = new DateTime()
        val vmd = new VMD("{cdn}/movies/"+fileName,
                       "Movie",
                       title,
                       List("tom", "frank"),
                       "steven",
                       List("carl", "louie"),
                       List("billy", "jennifer"),
                       1997,
                       "Action",
                       "h264",
                       true,
                       145,    // minutes
                       List(1024, 768),
                       "An epic adventure of delusional toad who thinks he's a prince.  You'll laugh, you'll cry, you'll potentially lose your grip on reality!"
                       + " Plus, he's really cute.  Look, watch this thing and I'll buy you lunch, damnit!",
                       Some(now),
                       Some(now)
                       )
        VMDDao.insert(vmd)
        // VMD.save(vmd)
        Ok("stored in the database")
    }

    def findById(id: String) = Action { implicit request =>
        println("findById request: "+request.uri)
        val movie = VMDDao.findById(id)
        Ok(_formatMovie(movie))
    }

    def findByName(fname: String) = Action { implicit request =>
        println("findByName request: "+request.uri)
        val movies = VMDDao.findByName(fname)
        Ok(_moviesListText(movies))
    }

    def findAll(limit: String = "25") = Action { implicit request =>
        println("findAll request: "+request.uri)
        val movies = VMDDao.findAll(limit.toInt)
        Ok(_moviesListText(movies))
    }


    def _formatMovie(movie: VMD) = {
        val text = movie.id + ":" +
            "\n    fileurl: "+movie.fileurl +
            "\n    vidtype: "+movie.vidtype +
            "\n    title: "+movie.title +
            "\n    writers: "+movie.writers +
            "\n    director: "+movie.director +
            "\n    producers: "+movie.producers +
            "\n    cast: "+movie.cast +
            "\n    year: "+movie.year +
            "\n    genre: "+movie.genre +
            "\n    encoding: "+movie.encoding +
            "\n    captions: "+movie.captions +
            "\n    duration (minutes): "+movie.duration +
            "\n    resolution: "+movie.resolution +
            "\n    description: "+movie.description +
            "\n    creationDate: "+movie.creationDate +
            "\n    modDate: "+movie.modDate +
            "\n"
        text
    }


    def _moviesListText(movies: List[VMD]): String = {
        var text = "" + movies.size + " results:\n\n"
        for (movie <- movies)
            text += _formatMovie(movie)
        text
    }
}
