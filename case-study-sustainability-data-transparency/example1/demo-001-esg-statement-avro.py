import random
import time
from avro import schema, datafile, io
from avro.datafile import DataFileReader
from avro.io import DatumReader

demoFilename="random_esg_statement.avro"

esg_goals = [   "No Poverty",
                "Zero Hunger",
                "Good Health and Well-being",
                "Quality Education",
                "Gender Equality",
                "Clean Water and Sanitation",
                "Affordable and Clean Energy",
                "Decent Work and Economic Growth",
                "Industry, Innovation, and Infrastructure",
                "Reduced Inequalities",
                "Sustainable Cities and Communities",
                "Responsible Consumption and Production",
                "Climate Action",
                "Life Below Water",
                "Life on Land",
                "Peace, Justice, and Strong Institutions",
                "Partnerships for the Goals"
            ]

# Define the Avro schema
avro_schema = schema.parse('''
{
  "type": "record",
  "name": "ESGStatement",
  "fields": [
    {"name": "timestamp", "type": "long"},
    {"name": "companyName", "type": "string"},
    {"name": "domain", "type": "string"},
    {"name": "registrationNumber", "type": "string"},
    {
      "name": "currentAssessment",
      "type": {
        "type": "map",
        "values": "int",
        "additionalProperties": false,
        "properties": {
          "type": "string",
          "enum": [
            "No Poverty",
            "Zero Hunger",
            "Good Health and Well-being",
            "Quality Education",
            "Gender Equality",
            "Clean Water and Sanitation",
            "Affordable and Clean Energy",
            "Decent Work and Economic Growth",
            "Industry, Innovation, and Infrastructure",
            "Reduced Inequalities",
            "Sustainable Cities and Communities",
            "Responsible Consumption and Production",
            "Climate Action",
            "Life Below Water",
            "Life on Land",
            "Peace, Justice, and Strong Institutions",
            "Partnerships for the Goals"
          ]
        }
      }
    },
    {
      "name": "targetValuesNextYear",
      "type": {
        "type": "map",
        "values": "int",
        "additionalProperties": false,
        "properties": {
          "type": "string",
          "enum": [
            "No Poverty",
            "Zero Hunger",
            "Good Health and Well-being",
            "Quality Education",
            "Gender Equality",
            "Clean Water and Sanitation",
            "Affordable and Clean Energy",
            "Decent Work and Economic Growth",
            "Industry, Innovation, and Infrastructure",
            "Reduced Inequalities",
            "Sustainable Cities and Communities",
            "Responsible Consumption and Production",
            "Climate Action",
            "Life Below Water",
            "Life on Land",
            "Peace, Justice, and Strong Institutions",
            "Partnerships for the Goals"
          ]
        }
      }
    }
  ]
}
''')

# Generate a random ESG statement
def generate_random_esg_statement():

    current_assessment = {}
    target_values_next_year = {}

    for goal in esg_goals:
        current_assessment[goal] = random.randint(0, 100)
        target_values_next_year[goal] = random.randint(0, 100)
        print( goal, ":", current_assessment[goal], target_values_next_year[goal] )

    # Generate a random timestamp
    timestamp = int(time.time())

    # Generate a random company name, domain, and registration number
    company_name = "Demo Company"
    domain = "democompany.com"
    registration_number = "1234567890"

    # Create the ESG statement
    esg_statement = {
        "timestamp": timestamp,
        "companyName": company_name,
        "domain": domain,
        "registrationNumber": registration_number,
        "currentAssessment": current_assessment,
        "targetValuesNextYear": target_values_next_year
    }

    print( esg_statement )

    return esg_statement

# Generate a random ESG statement and write it to an Avro file
def write_random_esg_statement_to_avro_file(filename):
    esg_statement = generate_random_esg_statement()

    with open(filename, 'wb') as avro_file:
        writer = datafile.DataFileWriter(avro_file, io.DatumWriter(), avro_schema)
        writer.append(esg_statement)
        writer.close()

    print("ESG statement written to Avro file:", filename)

# Example usage

print( )
print( "#### Write the data into an Avro file using our schema ... ")
print( )
write_random_esg_statement_to_avro_file( demoFilename )

print( )
print( "#### Read the data from our Avro file ... ")
print( )
# Open the Avro data file
with open(demoFilename, 'rb') as avro_file:
    # Create a data file reader
    reader = DataFileReader(avro_file, DatumReader())

    # Iterate over the Avro records
    for record in reader:
        # Access the fields of the record
        timestamp = record['timestamp']
        companyName = record['companyName']
        domain = record['domain']
        registrationNumber = record['registrationNumber']
        currentAssessment = record['currentAssessment']
        targetValuesNextYear = record['targetValuesNextYear']

        # Print the data
        print(f"Timestamp: {timestamp}")
        print(f"Company Name: {companyName}")
        print(f"Domain: {domain}")
        print(f"Registration Number: {registrationNumber}")
        print(f"Current Assessment: {currentAssessment}")
        print(f"Target Values Next Year: {targetValuesNextYear}")
        print()

    # Close the data file reader
    reader.close()