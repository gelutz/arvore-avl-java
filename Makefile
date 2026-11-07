.DEFAULT_GOAL := help

.PHONY: help run stub verify clean view

help: ## Lista os alvos disponíveis
	@grep -hE '^[a-z-]+:.*?## ' $(MAKEFILE_LIST) | awk 'BEGIN{FS=":.*?## "}{printf "  \033[36m%-10s\033[0m %s\n", $$1, $$2}'

run: ## Executa a aplicação (avl.Main)
	gradle run --console=plain -q

stub: ## Executa os testes (avl.Test)
	gradle stub

verify: ## Compila e executa os testes (gradle build)
	gradle build --console=plain -q

view: ## Abre a visualização da árvore (TamboUI, precisa de terminal de verdade)
	gradle installDist --console=plain -q
	java --enable-native-access=ALL-UNNAMED -cp 'build/install/arvore-avl-java/lib/*' avl.view.TreeView

clean: ## Remove artefatos de build
	gradle clean --console=plain -q
