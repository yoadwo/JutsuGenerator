curl -v -X PUT "http://localhost:8080/mockserver/clear" -d '{"path": "/jutsu"}'
curl -v -X PUT "http://localhost:8080/mockserver/expectation" -d '{
  "httpRequest" : {
    "method" : "POST",
    "path" : "/jutsu",
    "queryStringParameters" : {
      "handseals" : [ {"schema": {"type": "string" } } ]
    },
  },
  "httpResponse" : {
    "body" : "{ \"name\": \"earth style: earth spike jutsu\", \"prompt\": \"the ground where the training dummy stands splits apart and three large rocks erupt from the ground, impaling the training dummy from the bottom.\" }",
    "headers" : {
          "Content-Type" : [ "application/json" ],
          "access-control-allow-origin": ["http://localhost:4200", "https://yoadwo.github.io/"],
          "access-control-allow-methods": ["GET", "POST", "OPTIONS"],
          "access-control-allow-headers": ["*"]
        }
  }
}'
curl -v -X PUT "http://localhost:8080/mockserver/clear" -d '{"path": "/jutsu/visualize"}'
curl -v -X PUT "http://localhost:8080/mockserver/expectation" -d '{
  "httpRequest" : {
    "method" : "OPTIONS",
    "path" : "/jutsu/visualize",
  },
  "httpResponse" : {
    "headers" : {
          "Content-Type" : [ "application/json" ],
          "access-control-allow-origin": ["http://localhost:4200", "https://yoadwo.github.io/"],
          "access-control-allow-methods": ["GET", "POST", "OPTIONS"],
          "access-control-allow-headers": ["*"]
        }
  }
}'
curl -v -X PUT "http://localhost:8080/mockserver/expectation" -d '{
  "httpRequest" : {
    "method" : "POST",
    "path" : "/jutsu/visualize",
    "body" : { "type": "STRING" },
  },
  "httpResponse" : {
    "body" : "{ \"name\": \"earth style: earth spike jutsu\", \"prompt\": \"the ground where the training dummy stands splits apart and three large rocks erupt from the ground, impaling the training dummy from the bottom.\", \"generatedImageUrl\": \"https://placehold.co/600x400\" }",
    "headers" : {
      "Content-Type" : [ "application/json" ],
      "access-control-allow-origin": ["http://localhost:4200"],
      "access-control-allow-methods": ["GET", "POST", "OPTIONS"],
      "access-control-allow-headers": ["*"]
    },
    "delay": {
      "timeUnit": "SECONDS",
      "value": 5
    }
  }
}'

