const Web3 = require('web3')
const solc = require('solc')
const fs = require('fs')

const web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"))


console.log(web3.eth.accounts);

const code = fs.readFileSync('gameContestContract.sol').toString()
const compiledCode = solc.compile(code)

console.log(compiledCode)
