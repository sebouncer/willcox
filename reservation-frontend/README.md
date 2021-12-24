#Spedding.willcox.co.nz

AWS commands

aws s3 sync reservation-frontend/app/ s3://spedding-willcox-frontend-prod --delete --profile personal-sebouncer

aws s3api list-buckets --profile personal-sebouncer

aws cloudfront create-invalidation --distribution-id E2TTQER0KKJIAE --paths "/*" --profile personal-sebouncer