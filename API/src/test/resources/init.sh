# Check if the container exists
if [ "$(docker ps -q -f name=mockserver)" ]; then
    # If the container exists, stop and delete it
    docker stop mockserver
    docker rm mockserver
fi

# Run the Docker container
docker run -d --rm -p 8080:1080 --name mockserver mockserver/mockserver