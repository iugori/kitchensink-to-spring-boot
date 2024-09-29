help:
## help: Shows this helpful list of commands (works from Git Bash)
	@echo "Usage:"
	@sed -n 's/^##//p' ${MAKEFILE_LIST} | column -t -s ':' | sed -e 's/^/-/'



build-back-prod-image-step:
## build-back-prod-image-step: Builds the SpringBoot application production image
	docker build -f ./ops/ks-back-prod.dockerfile -t ks-back-image .

#run-back-prod-container-interactive:
#	docker run -it --rm --name ks-back-debug ks-back-image

start-back-prod-container-step:
## start-back-prod-container-step: Starts the SpringBoot application production container
	docker run -d -p 8911:8081 --rm --name ks-back ks-back-image 


build-front-prod-image-step:
## build-front-prod-image-step: Builds the Angular application production image
	docker build -f ./ops/ks-front-prod.dockerfile -t ks-front-image .

#run-front-prod-container-interactive:
#	docker run -it --rm --name ks-front-debug ks-front-image 

start-front-prod-container-step:
## start-front-prod-container-step: Starts the Angular application production container
	docker run -d -p 8910:80 --rm --name ks-front ks-front-image 


cleanup-prod-containers:
## cleanup-prod-containers: stops the production containers
	docker stop $(shell docker ps -a --filter "ancestor=ks-front-image" -q) 2>/dev/null || true
	docker stop $(shell docker ps -a --filter "ancestor=ks-back-image" -q) 2>/dev/null || true

start-back-prod: build-back-prod-image-step start-back-prod-container-step
## start-back-prod: Builds and starts the back-end container
	@echo [SUCCESS] Back-end container is up

start-front-prod: build-front-prod-image-step start-front-prod-container-step
## start-front-prod: Builds and starts the front-end container
	@echo [SUCCESS] Front-end container is up

demo: cleanup-prod-containers start-back-prod start-front-prod
## demo: Builds and starts the application demo in local environment
	@echo
	@echo Please navigate to http://localhost:8910 to visit the application page
	@echo
