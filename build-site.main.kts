@file:Repository("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
@file:DependsOn("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")

import kotlinx.html.*
import kotlinx.html.stream.createHTML
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

fun writePage(fileName: String, block: HTML.() -> Unit) {
	val html = createHTML().html(block = block)
	Files.writeString(
		Path.of(fileName),
		"<!DOCTYPE html>\n$html",
		StandardOpenOption.CREATE,
		StandardOpenOption.TRUNCATE_EXISTING
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
fun FlowOrMetaDataOrPhrasingContent.ogImage(imageUrl: String, width: Int, height: Int) {
	property("og:image", imageUrl)
	property("og:image:width", width.toString())
	property("og:image:height", height.toString())
}

fun FlowOrMetaDataOrPhrasingContent.useStyleCss() = link(rel = "stylesheet", href = "/style.css")

fun FlowContent.navDiv() {
	div {
		nav {
			a(href = "index.html") { +"Home" }
			+" | "
			a(href = "modpacks.html") { +"My Modpacks" }
			+" | "
			a(href = "mods.html") { +"My Mods" }
		}
	}
}

writePage("index.html") {
	head {
		meta(charset = "utf-8")
		title(content = "Homepage")
		link(rel = "icon", type = "image/png", href = "images/pfp.png")

		ogName("aaronhowser.dev")
		ogTitle("Homepage")
		ogDescription("The home page for aaronhowser.dev")
		ogImage("https://aaronhowser.dev/images/pfp.png", 48, 48)
		ogUrl("https://aaronhowser.dev")
		ogType("website")

		useStyleCss()
	}

	body {
		navDiv()

		div {
			style = "text-align: center"
			h1 { +"Welcome to aaronhowser.dev" }
			p {
				+"This is the home page for aaronhowser.dev. Here you can find links to my projects and learn more about me."
			}
		}

		div {
			h1 { +"About Me" }

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
			h1 { +"Projects" }

			p {
				+"I got my start working on Minecraft modpacks, which is mainly creative design and planning, writing documentation, and programming in JavaScript."
			}

			p {
				+"You can see the modpacks I've worked on at the "
				a(href = "modpacks.html") { +"modpacks page" }
				+"."
			}

			p {
				+"Since then, I've moved on to making Minecraft mods, which involves more in-depth programming in Java and Kotlin. You can see them all at the "
				a(href = "mods.html") { +"mods page" }
				+"."
			}

			p {
				+"I also currently work for "
				a(href = "https://www.youtube.com/@MrBeastGaming") { +"MrBeast Gaming" }
				+", making custom mods for their videos. You can see each video that I've worked on at the "
				a(href = "videos.html") { +"videos page" }
				+"."
			}

			hr {  }

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