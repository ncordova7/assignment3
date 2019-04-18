from faker import Faker
import csv

faker = Faker()

def writeToCSV():

    with open(fileName, 'w') as csvfile:
        fieldnames = ['first_name', 'last_name', 'email', 'phone_number']

        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

        writer.writeheader()
        for i in range(int(numTuples)):
            writer.writerow(
             {
                'first_name': faker.first_name(),
                'last_name': faker.last_name(),
                'email': faker.email(),
                'phone_number': faker.phone_number(),
            }
         )

if __name__=='__main__':
    fileName = input('What would you like to name your file? ')
    fileName = fileName + '.csv'
    numTuples = input('How many tuples would you like in your file? ')
    writeToCSV()


