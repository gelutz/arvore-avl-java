.DEFAULT_GOAL := help

.PHONY: help run stub verify clean

help: ## Lista os alvos disponíveis
	@grep -hE '^[a-z-]+:.*?## ' $(MAKEFILE_LIST) | awk 'BEGIN{FS=":.*?## "}{printf "  \033[36m%-10s\033[0m %s\n", $$1, $$2}'

run: ## Executa a aplicação (avl.Main)
	gradle run --console=plain -q

stub: ## Executa os testes (avl.Test)
	gradle stub

verify: ## Compila e executa os testes (gradle build)
	gradle build --console=plain -q

clean: ## Remove artefatos de build
	gradle clean --console=plain -q
