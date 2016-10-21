package io.gitlab.arturbosch.detekt.rules

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.jetbrains.spek.api.SubjectSpek
import org.jetbrains.spek.api.dsl.SubjectDsl
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

/**
 * @author Artur Bosch
 */
class UselessSemicolonSpec : SubjectSpek<UselessSemicolon>({
	subject { UselessSemicolon() }

	describe("common semicolon cases") {
		it("finds useless semicolon") {
			val code = """
			// here is a ; semicolon
            fun main() {
                fun name() { a(); return b }
                println(";")
                println();
            }
			"""
			execute(code)
			assertThat(subject.findings, hasSize(equalTo(1)))
		}

		it("does not deletes statement separation semicolon") {
			val code = """
            fun main() {
                fun name() { a();return b }
            };
            """
			execute(code)
			assertThat(subject.findings, hasSize(equalTo(1)))
		}

		it("should find two double semicolons") {
			val code = """
            fun main() {
                println();;;;println()
            }
            """
			execute(code)
			assertThat(subject.findings, hasSize(equalTo(2)))
		}
	}

})

private fun SubjectDsl<UselessSemicolon>.execute(code: String) {
	val root = loadAsFile(code)
	subject.visit(root)
}