// Library
var express = require('express');
var app = express();
var bodyParser = require('body-parser');

// Port
const PORT = 3000;

// HEADER SET
app.use(function (req, res, next) {
    res.header('Content-Type', 'application/json');
    next();
});

// USE BODY PARSER
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());



// START : 3000
app.listen(PORT,function(){
    console.log("server "+PORT+" starting");
});

// Lights CLASS
function Lights( id, name, resourceIndex){
    this.id = id;
    this.name = name;
    this.resourceIndex = resourceIndex;
}

// Lights.toString()
Lights.prototype.toString = function() {
    return  "\nid: "+ this.id + "\nname: "+this.name +  "\nresourceIndex: "+ this.resourceIndex+"\n";
};


// Default List
var lightsList = [
    new Lights( 0,"Room1 Table Lamp", 0),
    new Lights( 1,"livingRoom White Ceiling Lamp", 1),
    new Lights( 2,"bathRoom1 Lamp", 2),
    new Lights( 3,"Room2 Ceiling Lamp",1),
    new Lights( 4,"bathRoom1 Lamp ",0),
    new Lights( 5,"Room1 Table White Long Lamp" ,2)
]


// getList()
// return List<Object>
app.get("/lights/list", function(req, res){
    console.log("/lights/list--- totalSize() : " + lightsList.length );
    res.send(lightsList);

});

// getOneBy(object.id)
// return Object
app.get("/lights/:id", function(req, res){
    var id = parseInt(req.params.id);
    var lights = findLightsById(id);
    if(!lights){
        res.status(500).json({"error":"error","message":"Not Found Member"});
    }
    console.log("/lights/"+id+"------"+ lights.toString() );
    res.send(lights);

});


//Insert(object)
// return Object
app.post("/lights/new", function(req,res){
    var name = req.body.name;
    var resourceIndex = req.body.resourceIndex;
    var id = lightsList.length;
    var lightsNew =new Lights( id+1,name, resourceIndex);
    lightsList.push(lightsNew);
    console.log("/lights/new----"+ lightsNew.toString() );
    res.send(lightsNew);

});


// update(object)
// return Object
app.post("/lights/update", function (req, res) {
    var name = req.body.name;
    var resourceIndex = req.body.resourceIndex;
    var id = req.body.id;

    var index = findIndexByLightsId(id);
    var lights = new Lights(id, name, resourceIndex);
    lightsList[index] = lights;
    console.log("/lights/update----"+ lights.toString() );
    res.send(lights);

});



// DeleteBy(object)
// return index(in the List)
app.post("/lights/delete", function(req, res){
    var ligthsId = parseInt(req.body.id);
    var index = findIndexByLightsId(ligthsId);
    if(index != -1){
        lightsList.splice(index,1);
    }
    console.log("/lights/delete---- index: "+index );
    res.send(index+"");

});

// FindIndexBY(object.id)
// return index(in the List)
function findIndexByLightsId(id){
    var currentIndex = -1;
    lightsList.some(function (item, index, array) {
        if(item.id == id){
            currentIndex=index;
        }
        return (item.id==id);
    });
    return currentIndex;
}

// FindOneBY(object.id)
// return Object
function findLightsById(id){
    var lights;
    lightsList.some(function (item, index, array) {
        if(item.id == id){
            lights=item;
        }
        return (item.id==id);
    });
    return lights;
}

