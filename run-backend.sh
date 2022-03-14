cd ./discovery_service 
./run.sh &
cd ../auth_service
./run.sh &
cd ../api_gateway
./run.sh &
cd ../media_service
./run.sh
