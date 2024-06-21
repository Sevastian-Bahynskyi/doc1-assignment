const exec = require('child_process').exec;

process.env.NODE_ENV = 'production';
const cmd = 'react-scripts start';

exec(cmd, (error, stdout, stderr) => {
	if (error) {
		console.error(`exec error: ${error}`);
		return;
	}
	console.log(`stdout: ${stdout}`);
	console.error(`stderr: ${stderr}`);
});