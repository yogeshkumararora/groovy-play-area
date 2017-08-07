import groovy.json.JsonSlurper
import com.home.groovy.Vehicle

def reader = new FileReader('vehicle.json')
def jsonVehicle = new JsonSlurper().parse(reader)

def vehicle = new Vehicle (
        brand: jsonVehicle.brand,
        model: jsonVehicle.model,
        transmission: new Vehicle.Transmission(
                gears: jsonVehicle.transmission.gears/*,
                type: jsonVehicle.transmission.type*/),
        releaseYear: jsonVehicle.releaseYear,
        fuel: jsonVehicle.fuel)

println vehicle