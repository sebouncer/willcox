# willcox


AWS commands

mvn clean package -DskipTests && aws lambda update-function-code --function-name reservation --zip-file fileb://reservation-lambda/target/function.zip --profile personal-sebouncer
aws lambda update-function-code --function-name reservation --zip-file fileb://reservation-lambda/target/function.zip --profile personal-sebouncer
aws lambda get-function --function-name reservation --profile personal-sebouncer



aws dynamodb get-item \
    --table-name reservation \
    --key '{"id": {"S": "1"}}' \
    --profile personal-sebouncer >> currentRSVP.txt

    
    