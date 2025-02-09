#!/bin/bash

export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1

echo "Waiting for LocalStack to start SNS and SQS..."

# Loop to wait for LocalStack to be ready
until aws --endpoint-url=http://localstack:4566 sns list-topics > /dev/null 2>&1; do
  echo "Waiting for SNS to be available..."
  sleep 5
done

echo "SNS is available! Creating SNS topic and SQS queue..."

# Create SNS Topic
aws --endpoint-url=http://localstack:4566 sns create-topic --name MyTopic

# Create SQS Queue
aws --endpoint-url=http://localstack:4566 sqs create-queue --queue-name MyQueue

# Get the ARN of the SQS queue
SQS_ARN=$(aws --endpoint-url=http://localstack:4566 sqs get-queue-attributes \
    --queue-url http://localstack:4566/000000000000/MyQueue \
    --attribute-name QueueArn --region us-east-1 | jq -r '.Attributes.QueueArn')

# Get the ARN of the SNS topic
SNS_ARN=$(aws --endpoint-url=http://localstack:4566 sns list-topics --region us-east-1 | jq -r '.Topics[].TopicArn' | grep "MyTopic")

# Subscribe the SQS queue to SNS
echo "Subscribing SQS to SNS..."
aws --endpoint-url=http://localstack:4566 sns subscribe \
  --topic-arn "$SNS_ARN" \
  --protocol sqs \
  --notification-endpoint "$SQS_ARN"

# List all active subscriptions for SNS topics in LocalStack
aws --endpoint-url=http://localstack:4566 sns list-subscriptions --region us-east-1

echo "Configuration completed successfully!"