help:
## help: Shows this helpful list of commands (works from Git Bash)
	@echo "Usage:"
	@sed -n 's/^##//p' ${MAKEFILE_LIST} | column -t -s ':' | sed -e 's/^/-/'

build-back-step:
## build-back-step: Builds the SpringBoot application fat jar
	mvn -f ./ks-back/pom.xml clean install

build-back-prod-image-step:
## build-back-prod-image-step: Builds the SpringBoot application production image
	docker build -f ./ops/ks-back-prod.dockerfile -t ks-back-image .

start-back-prod-container-step:
## start-back-prod-container-step: Starts the SpringBoot application production container
	docker run -d -p 8081:8081 --rm --name ks-back ks-back-image 

build-front-prod-image-step:
## build-front-prod-image-step: Builds the Angular application production image
	docker build -f ./ops/ks-front-prod.dockerfile -t ks-front-image .

# https://www.youtube.com/watch?v=-o5l6zFJ9_o

run-front-prod-container-interactive:
## run-front-prod-container-interactive: Starts the Angular production container in interactive mode (requires ENTRYPOINT ["/bin/sh"])
	docker run -it --rm --name ks-front-debug ks-front-image 

