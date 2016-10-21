package io.gitlab.arturbosch.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.CodeSmellRule
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Location
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtProperty

/**
 * @author Artur Bosch
 */
class CommentOverPrivateProperty(config: Config = Config.EMPTY) : CodeSmellRule("CommentOverPrivateProperty", config) {

	override fun visitProperty(property: KtProperty) {
		val modifierList = property.modifierList
		if (modifierList != null && property.docComment != null) {
			if (modifierList.hasModifier(KtTokens.PRIVATE_KEYWORD)) {
				addFindings(CodeSmell(id, Location.of(property.docComment!!)))
			}
		}
	}

}