docker run -p 9000:9000 --name minio -e "MINIO_ACCESS_KEY=admin" -e "MINIO_SECRET_KEY=12345678" -v /mnt/minio/data:/data -v /mnt/minio/config:/root/.minio minio/minio server /data

