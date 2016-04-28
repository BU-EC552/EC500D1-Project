var exec = require('child_process').exec;
var fs = require('fs');

// Import events module
var events = require('events');
// Create an eventEmitter object
var eventEmitter = new events.EventEmitter();

var json_file = './output/EnhancedBLAT.json';
var json_parse;

var cmd = 'java -jar'//;EnhancedBLAT.jar -q atggcttgatcaatgggact';

if (process.argv.length > 2) 
{
process.argv.forEach(function (val, index, array) {
	if (index > 1)
		cmd += ' ' + val;
});
}

console.log('Execute: ' + cmd);

exec(cmd, function(error, data, stderr)
{
  // command output is in stdout
	if (error)
		return console.log(error);
	eventEmitter.emit('finish_jar', data);
});

var finish_process = function finish_process(data)
{
	//console.log('finish_process');
	json_parse = JSON.parse(fs.readFileSync(json_file, 'utf8'));
	eventEmitter.emit('print_json'); //Uncomment to print JSON file
}

var print_json = function print_json()
{
	console.log(json_parse);
}


eventEmitter.addListener('print_json', print_json);	

eventEmitter.addListener('finish_jar', finish_process);	

