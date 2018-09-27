all: 
	cd ../Driver; mvn clean install; cd -; \
	mvn clean install assembly:single 
clean:
	mvn clean
