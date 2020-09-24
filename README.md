# Documentation
There will be an attempt to better explaing this this workspace project at www.puglieseweb.com. Currently the documentation is scattered across multiple readme files: 
* https://github.com/puglieseweb/puglieseweb.com/tree/master/software-development/software-design
* https://github.com/puglieseweb/puglieseweb.com/tree/master/software-development/technologies/containers
* https://github.com/puglieseweb/puglieseweb.com/tree/master/software-development/guides


# Repository folder structure
## The "enviroment" folder
The [enviroments/docker-compose.yml](enviroment/docker-compose.yml) launches my own development environments running services such as the Confluent Platform.

The deodorized environment provide:
* Docker Engine nodes capable of running containers
* Management of these notes through a single API endpoint with Swarm
* Overlay networks allowing communication between containers across nodes
* Internal image distribution with a secure (but not-HA) registry
* Kibana can be reached on port 5601

## The "apps" folder
The apps directivity contains trainings and sample applications leveraging the deodorized environments 




