@file:Repository("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

val SITE_URL = "https://aaronhowser.dev"
val SITE_NAME = "aaronhowser.dev"
val DEFAULT_IMAGE_URL = "$SITE_URL/images/pfp.png"

fun writePage(fileName: String, block: HTML.() -> Unit) {
	require(fileName.endsWith(".html")) { "Page output must be an HTML file: $fileName" }

	val html = createHTML().html(block = block)
	val outputPath = Path.of(fileName)
	outputPath.parent?.let { Files.createDirectories(it) }
	Files.writeString(
		outputPath,
		"<!DOCTYPE html>\n$html",
		StandardCharsets.UTF_8,
		StandardOpenOption.CREATE,
		StandardOpenOption.TRUNCATE_EXISTING,
		StandardOpenOption.WRITE
	)

	println("Wrote $fileName")
}

fun FlowOrMetaDataOrPhrasingContent.property(property: String, content: String) {
	meta {
		attributes["property"] = property
		this.content = content
	}
}

fun FlowOrMetaDataOrPhrasingContent.ogName(content: String) = property("og:site_name", content)
fun FlowOrMetaDataOrPhrasingContent.ogTitle(content: String) = property("og:title", content)
fun FlowOrMetaDataOrPhrasingContent.ogDescription(content: String) = property("og:description", content)
fun FlowOrMetaDataOrPhrasingContent.ogUrl(content: String) = property("og:url", content)
fun FlowOrMetaDataOrPhrasingContent.ogType(content: String) = property("og:type", content)
fun FlowOrMetaDataOrPhrasingContent.ogImage(imageUrl: String, width: Int, height: Int, alt: String) {
	property("og:image", imageUrl)
	property("og:image:width", width.toString())
	property("og:image:height", height.toString())
	property("og:image:alt", alt)
}

fun FlowOrMetaDataOrPhrasingContent.useStyleCss() = link(rel = "stylesheet", href = "/style.css")

fun HEAD.commonHead(
	pageTitle: String,
	ogTitleText: String,
	ogDescriptionText: String,
	ogUrlText: String,
	ogImageUrl: String = DEFAULT_IMAGE_URL,
	ogImageW: Int = 48,
	ogImageH: Int = 48,
	ogImageAlt: String = "Aaron Howser"
) {
	meta(charset = "utf-8")
	meta(name = "viewport", content = "width=device-width, initial-scale=1")
	meta(name = "description", content = ogDescriptionText)
	title(content = pageTitle)
	link(rel = "canonical", href = ogUrlText)
	link(rel = "icon", type = "image/png", href = "/images/pfp.png")

	ogName(SITE_NAME)
	ogTitle(ogTitleText)
	ogDescription(ogDescriptionText)
	ogImage(ogImageUrl, ogImageW, ogImageH, ogImageAlt)
	ogUrl(ogUrlText)
	ogType("website")
	property("og:locale", "en_US")
	meta(name = "twitter:card", content = "summary")

	useStyleCss()
}

val indexPage = "index.html"
val modpacksPage = "modpacks.html"
val modsPage = "mods.html"
val videosPage = "videos.html"

fun FlowContent.siteNav(currentPage: String) {
	nav {
		attributes["aria-label"] = "Main navigation"

		fun navLink(href: String, label: String) {
			a(href = href) {
				if (href == currentPage) attributes["aria-current"] = "page"
				+label
			}
		}

		navLink(indexPage, "Home")
		+" | "
		navLink(modsPage, "My Mods")
		+" | "
		navLink(modpacksPage, "My Modpacks")
		+" | "
		navLink(videosPage, "Videos I've Worked On")
	}
}

writePage(indexPage) {
	attributes["lang"] = "en"
	head {
		commonHead(
			pageTitle = "Homepage",
			ogTitleText = "Homepage",
			ogDescriptionText = "The home page for aaronhowser.dev",
			ogUrlText = "https://aaronhowser.dev/"
		)
	}

	body {
		siteNav(indexPage)

		div {
			style = "text-align: center"
			h1 { +"Welcome to aaronhowser.dev" }
			p {
				+"This is the home page for aaronhowser.dev. Here you can find links to my projects and learn more about me."
			}
		}

		div {
			h2 { +"About Me" }

			p {
				+"I am a software developer who's worked on a variety of projects, in a variety of languages."
			}

			ul {
				li { +"Kotlin: 4+ years" }
				li { +"Java: 6+ years" }
				li { +"JavaScript: 5+ years" }
				li { +"TypeScript: <1 year" }
			}

			p {
				+"As you've probably noticed, neither HTML nor CSS are on that list."
			}
		}

		hr { }

		div {
			h2 { +"Projects" }

			p {
				+"I got my start working on Minecraft modpacks, which is mainly creative design and planning, writing documentation, and programming in JavaScript."
			}

			p {
				+"You can see the modpacks I've worked on at the "
				a(href = modpacksPage) { +"modpacks page" }
				+"."
			}

			p {
				+"Since then, I've moved on to making Minecraft mods, which involves more in-depth programming in Java and Kotlin. You can see them all at the "
				a(href = modsPage) { +"mods page" }
				+"."
			}

			p {
				+"I also currently work for "
				a(href = "https://www.youtube.com/@MrBeastGaming") { +"MrBeast Gaming" }
				+", making custom mods for their videos. You can see each video that I've worked on at the "
				a(href = videosPage) { +"videos page" }
				+"."
			}

			hr { }

			ul {
				li {
					a(href = "https://modrinth.com/user/aaronhowser1") { +"My Modrinth" }
				}
				li {
					a(href = "https://www.curseforge.com/members/aaronhowser1/projects") { +"My CurseForge" }
				}
				li {
					a(href = "https://github.com/aaronhowser1") { +"My GitHub" }
				}
				li {
					a(href = "https://github.com/Berry-Club") { +"My GitHub Organization" }
				}
			}
		}
	}

}

writePage(modpacksPage) {
	attributes["lang"] = "en"
	head {
		commonHead(
			pageTitle = "My Modpacks",
			ogTitleText = "My Modpacks",
			ogDescriptionText = "My modpacks",
			ogUrlText = "https://aaronhowser.dev/modpacks.html"
		)
	}

	body {
		siteNav(modpacksPage)

		fun Tag.modpackLink(name: String, cf: String? = null, ftb: String? = null) {
			+name

			if (!name.last().isWhitespace() && (cf != null || ftb != null)) {
				+" "
			}

			if (cf != null) {
				this@body.a(href = cf) { +"(CurseForge)" }
				if (ftb != null) {
					+" "
				}
			}
			if (ftb != null) {
				this@body.a(href = ftb) { +"(FTB)" }
			}
		}

		div {
			h1 { +"My Modpacks" }

			p { +"I was the sole developer of the following modpacks:" }

			ul {
				li {
					modpackLink(
						name = "FTB Academy 1.12",
						cf = "https://www.curseforge.com/minecraft/modpacks/ftb-academy",
						ftb = "https://www.feed-the-beast.com/modpacks/1-ftb-academy-112"
					)
				}

				li {
					modpackLink(
						name = "FTB Academy 1.16",
						ftb = "https://www.feed-the-beast.com/modpacks/88-ftb-academy-116"
					)
				}

				li {
					modpackLink(
						name = "FTB University 1.12",
						ftb = "https://www.feed-the-beast.com/modpacks/52-ftb-university-112"
					)
					ul {
						li {
							+"(Won "
							a(href = "https://www.reddit.com/r/feedthebeast/") { +"/r/feedthebeast" }
							+"'s \"Best Modpack 2020\" award!)"
						}
					}
				}

				li {
					modpackLink(
						name = "FTB University 1.16",
						ftb = "https://www.feed-the-beast.com/modpacks/90-ftb-university-116"
					)
				}

				li {
					modpackLink(
						name = "FTB University 1.19",
						ftb = "https://www.feed-the-beast.com/modpacks/108-ftb-university-119"
					)
				}
			}

			p { +"I was a contributor to the following modpacks:" }

			ul {
				li {
					modpackLink(
						name = "FTB Infinity Evolved",
						cf = "https://www.curseforge.com/minecraft/modpacks/ftb-infinity-evolved",
						ftb = "https://www.feed-the-beast.com/modpacks/23-ftb-infinity-evolved-17"
					)
				}

				li {
					modpackLink(
						name = "FTB Infinity Evolved Skyblock",
						cf = "https://www.curseforge.com/minecraft/modpacks/ftb-infinity-evolved-skyblock",
						ftb = "https://www.feed-the-beast.com/modpacks/20-ftb-infinity-evolved-skyblock"
					)
				}

				li {
					modpackLink(
						name = "FTB Presents Stoneblock 2",
						cf = "https://www.curseforge.com/minecraft/modpacks/ftb-presents-stoneblock-2",
						ftb = "https://www.feed-the-beast.com/modpacks/4-ftb-presents-stoneblock-2"
					)
				}

				li {
					modpackLink(
						name = "FTB Continuum",
						cf = "https://www.curseforge.com/minecraft/modpacks/ftb-continuum",
						ftb = "https://www.feed-the-beast.com/modpacks/34-ftb-continuum"
					)
				}

				li {
					modpackLink(
						name = "FTB Arcanum Institute",
						cf = "https://www.curseforge.com/minecraft/modpacks/ftb-arcanum-institute",
						ftb = "https://www.feed-the-beast.com/modpacks/115-ftb-arcanum-institute"
					)
				}

				li {
					modpackLink(
						name = "FTB Stoneblock 3",
						ftb = "https://www.feed-the-beast.com/modpacks/100-ftb-stoneblock-3",
						cf = "https://www.curseforge.com/minecraft/modpacks/ftb-stoneblock-3"
					)
				}

				li {
					modpackLink(
						name = "FTB Inferno",
						cf = "https://www.curseforge.com/minecraft/modpacks/ftb-inferno",
						ftb = "https://www.feed-the-beast.com/modpacks/99-ftb-inferno"
					)
				}

				li {
					modpackLink(
						name = "FTB Plexiglass Mountain",
						ftb = "https://www.feed-the-beast.com/modpacks/96-ftb-plexiglass-mountain"
					)
				}

				li {
					modpackLink(
						name = "FTB Skies",
						ftb = "https://www.feed-the-beast.com/modpacks/103-ftb-skies"
					)
				}

				li {
					modpackLink(
						name = "FTB Legend of the Eyes",
						ftb = "https://www.feed-the-beast.com/modpacks/102-ftb-legend-of-the-eyes"
					)
				}
			}
		}
	}
}

writePage(modsPage) {
	attributes["lang"] = "en"
	head {
		commonHead(
			pageTitle = "My Mods",
			ogTitleText = "My Mods",
			ogDescriptionText = "My mods",
			ogUrlText = "https://aaronhowser.dev/mods.html"
		)
	}

	body {
		siteNav(modsPage)

		div {
			h1 { +"My Mods" }

			fun UL.modLink(
				name: String,
				url: String,
				description: String
			) {
				li {
					a(href = url) { +name }
					+": $description"
				}
			}

			ul {

				modLink(
					name = "Genetics: Resequenced",
					url = "https://www.curseforge.com/minecraft/mc-mods/genetics-resequenced",
					description = "Get powers from mobs"
				)

				modLink(
					name = "Irregular Implements",
					url = "https://www.curseforge.com/minecraft/mc-mods/irregular-implements",
					description = "A modern remake of Random Things"
				)

				modLink(
					name = "Pitch Perfect",
					url = "https://www.curseforge.com/minecraft/mc-mods/pitch-perfect",
					description = "Instruments and an in-game music creator"
				)

				modLink(
					name = "Ariadne's Thread",
					url = "https://www.curseforge.com/minecraft/mc-mods/ariadnes-thread",
					description = "Shows your path through the world"
				)

				modLink(
					name = "LoFi Records to Mine To",
					url = "https://www.curseforge.com/minecraft/mc-mods/lofi-records-to-mine-to",
					description = "Dozens of new Music Discs"
				)

				modLink(
					name = "No Potion Icons",
					url = "https://www.curseforge.com/minecraft/mc-mods/no-potion-icons",
					description = "No potion icons"
				)

				modLink(
					name = "Aaron",
					url = "https://www.curseforge.com/minecraft/mc-mods/aaron",
					description = "Library mod for my other mods"
				)
			}

			p { +"Mods in progress:" }

			ul {
				modLink(
					name = "Paracosm",
					url = "https://github.com/Berry-Club/Paracosm",
					description = "A magic mod powered by Whimsy and childlike imagination!"
				)

				modLink(
					name = "Quiverbow: Refletched",
					url = "https://github.com/aaronhowser1/Quiverbow-Refletched",
					description = "A modern remake of Quiverbow"
				)
			}
		}
	}
}

writePage(videosPage) {
	attributes["lang"] = "en"
	head {
		commonHead(
			pageTitle = "Videos I've Worked On",
			ogTitleText = "My videos",
			ogDescriptionText = "Videos I've worked on",
			ogUrlText = "https://aaronhowser.dev/videos.html"
		)
	}

	body {
		siteNav(videosPage)

		div {
			h1 { +"Videos I've Worked On" }

			p {
				+"I currently work for "
				a(href = "https://www.youtube.com/@MrBeastGaming") { +"MrBeast Gaming" }
				+", making custom mods for their videos. Here are the videos I've worked on:"
			}

			fun embedYoutube(
				videoId: String,
				title: String,
				width: Int = 560,
				height: Int = 315
			) {
				div {
					style = "text-align:center"
					iframe {
						this.width = width.toString()
						this.height = height.toString()
						this.src = "https://www.youtube.com/embed/$videoId"
						attributes["title"] = title
						attributes["frameborder"] = "0"
						attributes["loading"] = "lazy"
						attributes["referrerpolicy"] = "strict-origin-when-cross-origin"
						attributes["allow"] = "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
						attributes["allowfullscreen"] = "true"
					}
				}
			}

			embedYoutube(videoId = "zp_S2Uwjb-M", title = "MrBeast Gaming video")
			p {}
			embedYoutube(videoId = "usGPK2hHomI", title = "MrBeast Gaming video")
			p {}
			embedYoutube(videoId = "9OHRtUHezTk", title = "MrBeast Gaming video")
			p {}
			embedYoutube(videoId = "ICNtItWYMNE", title = "MrBeast Gaming video")
			p {}
			embedYoutube(videoId = "wyKNY1_HnTc", title = "MrBeast Gaming video")
			p {}
			embedYoutube(videoId = "Caxv8tLATnc", title = "MrBeast Gaming video")
		}
	}
}
