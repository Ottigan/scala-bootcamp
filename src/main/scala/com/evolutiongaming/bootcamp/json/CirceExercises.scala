package com.evolutiongaming.bootcamp.json

import java.time.{Instant, LocalDate, Year}

import cats.syntax.either._
import cats.syntax.functor._
import io.circe._
import io.circe.Decoder.Result
import io.circe.generic.JsonCodec
import io.circe.parser._
import io.circe.syntax._
import Models._
import monocle.Traversal

// Use `sbt testOnly com.evolutiongaming.bootcamp.json.CirceExercisesSpec`
object CirceExercises {

  object basics {
    /*
    JSON keys are strings;
    JSON values are one of the following types:
    - Object
    - Array
    - String
    - Number
    - Boolean
    - Null
     */
    val jTrue: Json = Json.fromBoolean(true) // or Json.True
    val jString: Json = Json.fromString("just string")
    val jNumber: Json = Json.fromInt(10)
    val jNull: Json = Json.Null
    val jObj: Json = Json.obj(
      "boolean" -> jTrue,
      "string" -> jString,
      "number" -> jNumber,
      "null" -> jNull
    )
    val jArr: Json = Json.arr(jObj, jObj)
    jArr.spaces2 // jArr.noSpaces

    /* Exercise 1: represent raw JSON in circe types:
    {
      "title": "The Matrix",
      "year": 1999,
      "actors": ["Keanu Reeves", "Carrie-Anne Moss", "Laurence Fishburne"],
      "isRatedR" true
    }
     */

    val movie = Json.fromString("The Matrix")
    val year = Json.fromInt(1999)
    val keanu = Json.fromString("Keanu Reeves")
    val moss = Json.fromString("Carrie-Anne Moss")
    val laurence = Json.fromString("Laurence Fishburne")
    val actors = Json.arr(keanu, moss, laurence)
    val isTrue = Json.True

    lazy val jMatrix: Json = Json.obj(
      "title" -> movie,
      "year" -> year,
      "actors" -> actors,
      "isRatedR" -> isTrue
    )

    /* Parsing */
    val twinPeaksRawJson: String =
      """
        |{
        |  "show": "Twin Peaks",
        |  "ratings": [
        |    { "season": 1, "metaScore": 96 },
        |    { "season": 2, "metaScore": 95 },
        |    { "season": 3, "metaScore": 74 }
        |  ]
        |}""".stripMargin
    val twinPeaksParsed: Json =
      parse(twinPeaksRawJson).getOrElse(Json.Null)
    val tpCursor: HCursor = twinPeaksParsed.hcursor

    /* Transform */
    val oldGoodTwinPeaks: Json = tpCursor
      .downField("show")
      .withFocus(_.mapString("Old good " + _))
      .field("ratings")
      .withFocus(_.mapArray(_.init))
      .top
      .getOrElse(Json.Null)
    oldGoodTwinPeaks.spaces2

    /* Exercise 2: send the band to the tour */
    val killersRawJson: String =
      """
        |{
        |  "artist": {
        |    "name": "The Killers",
        |    "ontour": false,
        |    "stats": {
        |      "listeners": 4517050,
        |      "playcount": 216877854
        |    },
        |    "genres": ["indie rock", "alternative rock", "new wave"],
        |    "members": [
        |      { "name": "Brandon Flowers", "instruments": ["vocals", "keyboard", "bass"] },
        |      { "name": "Dave Keuning", "instruments": ["lead guitar"] },
        |      { "name": "Mark Stoermer", "instruments": ["bass", "rhythm guitar"] },
        |      { "name": "Ronnie Vanucci Jr.", "instruments": ["drums", "percussion"] }
        |    ],
        |    "url": "https://www.last.fm/music/The+Killers"
        |  }
        |}
        |""".stripMargin
    val killersParsed: Json = parse(killersRawJson).getOrElse(Json.Null)
    val killersCursor: HCursor = killersParsed.hcursor
    lazy val killersOnTourJson: Json = killersCursor
      .downField("artist")
      .downField("ontour")
      .withFocus(_ => Json.True)
      .top
      .getOrElse(Json.Null)
    killersOnTourJson.spaces2
  }

  /* Optics */
  object optics {
    import io.circe.optics.JsonPath._
    import monocle.Optional
    import basics.{killersRawJson, twinPeaksParsed}

    val _playCount: Optional[Json, Int] = root.artist.stats.playcount.int
    val _genres: Traversal[Json, String] = root.artist.genres.each.string
    val parsedKillersJson: Json = parse(killersRawJson).getOrElse(Json.Null)
    val playCount: Option[Int] = _playCount.getOption(parsedKillersJson)
    val allGenres: List[String] = _genres.getAll(parsedKillersJson)

    val _oldGoodTwinPeaks: Json => Json = root.ratings.arr.modify(_.init)
    val oldGoodTwinPeaks: Json = _oldGoodTwinPeaks(twinPeaksParsed)

    /* Exercise 3: same as 2, but using optics */
    val _onTour: Json => Json = root.artist.ontour.boolean.modify(_ => true)
    lazy val killersOnTourJson: Json = _onTour(parsedKillersJson)
  }

  /* Encoding/decoding, part I */
  val intsJson: Json = List(1, 2, 3).asJson
  val stringJson: Json = "Crystal".asJson
  val decodedInts: Either[Error, List[Int]] = decode[List[Int]]("[1, 2, 3]")
  val decodedString: Either[Error, String] = decode[String]("\"Crystal\"")

  val gig: Gig = Gig(
    venue = "London",
    date = LocalDate.of(2009, 7, 6),
    setlist = Seq("Sam's Town", "When You Were Young")
  )

  object semiauto {
    import io.circe.generic.semiauto._

    implicit val gigDecoder: Decoder[Gig] = deriveDecoder[Gig]
    implicit val gigEncoder: Encoder[Gig] = deriveEncoder[Gig]
    // or implicit val gigCodec = deriveCodec[Gig]

    val gigJson: Json = gig.asJson
    val decodedGig: Either[Error, Gig] = decode(gigJson.noSpaces)

    @JsonCodec final case class Song(title: String, lengthInSec: Int)
    val song: Song = Song("Crystal", 259)
    val songJson: Json = song.asJson

    /* Exercise 4:
      1) Create final case class Album with the following fields:
        - `title` of type String
        - `year` of type Int
        - `songs` of type Seq[Song]
      2) Put encoder and decoder for this class in scope
      3) Create album, then encode and decode it back

      What will happen if you comment codecs for `Song`?
     */
    @JsonCodec case class Album(title: String, year: Int, songs: Seq[Song])

    // implicit val albumEncoder = deriveEncoder[Album]
    // implicit val albumDecoder = deriveDecoder[Album]

    val randomAlbum = Album("Naruto", 1991, Seq(Song("Woof", 546)))

    lazy val albumJson: Json = randomAlbum.asJson
    val decodedAlbum: Either[Error, Album] = decode[Album](albumJson.noSpaces)
  }

  /* Encoding/decoding, part II */
  object auto {
    import io.circe.generic.auto._

    final case class Song(title: String, length: Int)
    private val song = Song("Crystal", 249)
    val songJson: Json = song.asJson
    val decodedSong: Either[Error, Song] = decode[Song](songJson.noSpaces)
  }

  object manual {
    final case class Song(title: String, length: Int)
    private val song = Song("Crystal", 249)

    implicit val songDecoder: Decoder[Song] =
      Decoder.forProduct2("title", "length")(Song.apply)
    implicit val songEncoder: Encoder[Song] =
      Encoder.forProduct2("title", "length")(s => (s.title, s.length))

    val songJson: Json = song.asJson
    val decodedSong: Either[Error, Song] = decode[Song](songJson.noSpaces)

    /* Exercise 5: same as 4, but with manual codecs */
    case class Album(title: String, year: Int, songs: Seq[Song])
    val randomAlbum = Album("Naruto", 1991, Seq(Song("Woof", 546)))

    implicit val albumEncoder: Encoder[Album] =
      Encoder.forProduct3("title", "year", "songs")(a => (a.title, a.year, a.songs))
    implicit val albumDecoder: Decoder[Album] = Decoder.forProduct3("title", "year", "songs")(Album.apply)
    lazy val albumJson: Json = randomAlbum.asJson
    val decodedAlbum: Either[Error, Album] = decode[Album](albumJson.noSpaces)
  }

  /* Encoding/decoding, part III */
  object custom1 {
    implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)

    implicit val decodeInstant: Decoder[Instant] =
      Decoder.decodeString.emap(str => Either.catchNonFatal(Instant.parse(str)).leftMap(err => "Instant: " + err.getMessage))

    @JsonCodec final case class TimeWindow(before: Instant, after: Instant)
    val timeWindow: TimeWindow = TimeWindow(
      before = Instant.now,
      after = Instant.now.plusSeconds(5L)
    )
    val timeWindowJson: Json = timeWindow.asJson

    /* Exercise 6: write custom codec for java.time.Year using existing one for Int */
    implicit lazy val encodeYear: Encoder[Year] = Encoder.encodeInt.contramap[Year](_.getValue)
    implicit lazy val decodeYear: Decoder[Year] =
      Decoder.decodeInt.emap(year => Either.catchNonFatal(Year.of(year)).leftMap(err => "Date: " + err.getMessage))
  }

  object snake_case {
    import io.circe.generic.extras._
    import custom1.{encodeYear, decodeYear}

    implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames

    @ConfiguredJsonCodec final case class Movie(title: String, year: Year, isRatedR: Boolean)
    val dieHard: Movie = Movie("Die Hard", Year.of(1988), isRatedR = true)
    lazy val dieHardJson: Json = dieHard.asJson // {"title":"Die Hard","year":1988,"is_rated_r":true}
  }

  object adt {
    import io.circe.generic.extras._
    import io.circe.generic.extras.semiauto._

    implicit val config: Configuration = Configuration.default
      .copy(transformConstructorNames = _.toLowerCase)

    implicit val genreCodec: Codec[Genre] = deriveEnumerationCodec[Genre]
    val `hip-hop`: Genre = Genre.`Hip-Hop`
    val hhJson: Json = `hip-hop`.asJson

    sealed trait Video
    final case class Movie(rating: Double) extends Video
    final case class Youtube(views: Long) extends Video
    implicit val movieDecoder: Decoder[Movie] = deriveConfiguredDecoder[Movie]
    implicit val movieEncoder: Encoder[Movie] = deriveConfiguredEncoder[Movie]
    implicit val ytDecoder: Decoder[Youtube] = deriveConfiguredDecoder[Youtube]
    implicit val ytEncoder: Encoder[Youtube] = deriveConfiguredEncoder[Youtube]
    implicit val videoDecoder: Decoder[Video] =
      List[Decoder[Video]](movieDecoder.widen, ytDecoder.widen).reduceLeft(_ or _)
    implicit val videoEncoder: Encoder[Video] = Encoder.instance {
      case m: Movie    => m.asJson
      case yt: Youtube => yt.asJson
    }

    /* Exercise 7: write codecs for classes in Models, create some artists and encode them */
    val someSinger: Musician = Musician("Brandon Flowers", MusicianKind.Singer)
    val someGuitar: Musician = Musician("Dave Keuning", MusicianKind.Guitar)
    val someBass: Musician = Musician("Mark Stoermer", MusicianKind.Bass)
    val brandonFlowers: SoloMusician = SoloMusician(someSinger, Genre.Pop, gigs = Seq.empty)
    val daveKeuning: SoloMusician = SoloMusician(someGuitar, Genre.Rock, gigs = Seq.empty)
    val markStoermer: SoloMusician = SoloMusician(someBass, Genre.Rock, gigs = Seq.empty)
    val theKillers: Artist = Band(
      title = "The Killers",
      members = Seq(
        brandonFlowers.musician,
        daveKeuning.musician,
        markStoermer.musician,
        Musician("Ronnie Vanucci Jr.", MusicianKind.Drums)
      ),
      genre = Genre.Rock,
      gigs = Seq(gig)
    )
    val ye: Artist = SoloMusician(
      musician = Musician("Kanye West", MusicianKind.Singer),
      genre = Genre.`Hip-Hop`,
      gigs = Seq.empty
    )
    val artists = Seq(theKillers, ye)

    implicit val musicianKindCodec: Codec[MusicianKind] = deriveEnumerationCodec[MusicianKind]
    implicit val gigEncoder: Encoder[Gig] = deriveConfiguredEncoder[Gig]
    implicit val gigDecoder: Decoder[Gig] = deriveConfiguredDecoder[Gig]
    implicit val musicianEncoder: Encoder[Musician] = deriveConfiguredEncoder[Musician]
    implicit val musicianDecoder: Decoder[Musician] = deriveConfiguredDecoder[Musician]
    implicit val soloMusicianEncoder: Encoder[SoloMusician] = deriveConfiguredEncoder[SoloMusician]
    implicit val soloMusicianDecoder: Decoder[SoloMusician] = deriveConfiguredDecoder[SoloMusician]
    implicit val bandEncoder: Encoder[Band] = deriveConfiguredEncoder[Band]
    implicit val bandDecoder: Decoder[Band] = deriveConfiguredDecoder[Band]

    implicit val artistEncoder: Encoder[Artist] = Encoder.instance {
      case solo: SoloMusician => solo.asJson
      case band: Band         => band.asJson
    }
    implicit val artistDecoder: Decoder[Artist] =
      List[Decoder[Artist]](soloMusicianDecoder.widen, bandDecoder.widen).reduceLeft(_ or _)

    lazy val artistsJson: Json = artists.asJson
  }

  /* Homework
   * Create models json parsers for last.fm API getUserInfo
   *
   */

}
