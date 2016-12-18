package au.id.tmm.utilities

import java.nio.file.{Path, Paths}

import au.id.tmm.utilities.hashing.Digest

trait TestingFiles {
  val fileToTest: Path = Paths.get(getClass.getResource("/soliloquy.txt").toURI)
  val fileToTestDigest: Digest = Digest("e37f03dc379fe6baa26298120b0f0a32b8bd366e510ce08c9fa350bf97e99e4f")

  val missingFile: Path = fileToTest.resolveSibling("missingFile.txt")

  val fileToTestContent: String = """To be, or not to be - that is the question:
                                    |Whether 'tis nobler in the mind to suffer
                                    |The slings and arrows of outrageous fortune
                                    |Or to take arms against a sea of troubles
                                    |And by opposing end them. To die, to sleep-
                                    |No more-and by a sleep to say we end
                                    |The heartache, and the thousand natural shocks
                                    |That flesh is heir to. 'Tis a consummation
                                    |Devoutly to be wished. To die, to sleep-
                                    |To sleep-perchance to dream: ay, there's the rub,
                                    |For in that sleep of death what dreams may come
                                    |When we have shuffled off this mortal coil,
                                    |Must give us pause. There's the respect
                                    |That makes calamity of so long life.
                                    |For who would bear the whips and scorns of time,
                                    |Th' oppressor's wrong, the proud man's contumely
                                    |The pangs of despised love, the law's delay,
                                    |The insolence of office, and the spurns
                                    |That patient merit of th' unworthy takes,
                                    |When he himself might his quietus make
                                    |With a bare bodkin? Who would fardels bear,
                                    |To grunt and sweat under a weary life,
                                    |But that the dread of something after death,
                                    |The undiscovered country, from whose bourn
                                    |No traveller returns, puzzles the will,
                                    |And makes us rather bear those ills we have
                                    |Than fly to others that we know not of?
                                    |Thus conscience does make cowards of us all,
                                    |And thus the native hue of resolution
                                    |Is sicklied o'er with the pale cast of thought,
                                    |And enterprise of great pitch and moment
                                    |With this regard their currents turn awry
                                    |And lose the name of action. - Soft you now,
                                    |The fair Ophelia! - Nymph, in thy orisons
                                    |Be all my sins remembered.""".stripMargin
}
