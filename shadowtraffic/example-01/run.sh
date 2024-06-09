# docker run -v $(pwd)/transactions.json:/home/config.json shadowtraffic/shadowtraffic:latest --config /home/config.json --sample 10 --stdout --watch
docker run --env-file license.env \
 -v $(pwd)/transactions_$1.json:/home/config.json shadowtraffic/shadowtraffic:latest \
 --config /home/config.json \
 --sample 1000 \
# --watch \
# --stdout
