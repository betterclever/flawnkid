const Web3 = require('web3')
const solc = require('solc')
const fs = require('fs')

class GameContract {

    contractAddress = null;
    GameContestContract = null;

    constructor() {
        const web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"))

        const code = fs.readFileSync('gameContestContract.sol').toString()
        const compiledCode = solc.compile(code)

        const abiDefinition = JSON.parse(compiledCode.contracts[':GameContest'].interface)

        this.GameContestContract = web3.eth.contract(abiDefinition)
        const byteCode = compiledCode.contracts[':GameContest'].bytecode

        const deployedContract = GameContestContract.new(0, { data: byteCode, from: web3.eth.accounts[0], gas: 4700000 })
        this.contractAddress = deployedContract.contractAddress
    }

    function submitNewIdea(topic, description, id, amount) {
        if (this.contractInstance != null) {
            this.contractInstance.newGameIdea(topic, description, {
                from: web3.eth.accounts[0],
            }, (err, val) => {
                this.contractInstance.addStake(val, {
                    from: web3.eth.accounts[id],
                    gas: 470000, value: web3.toWei(amount, 'ether')
                })
            })
        }
    }

    function addStake(ideaID, userID, amount) {
        const contractInstance = GameContestContract.at(contractAddress)
        contractInstance.addStake(ideaID, {
                from: web3.eth.accounts[userID],
                gas: 470000, value: web3.toWei(amount, 'ether')
        })
    }

    function getAllIdeas() {
        const contractInstance = GameContestContract.at(contractAddress)
        contractInstance.
    }
    
}





// contractInstance.newIdeaSubmitted((id) => {
//     console.log("hello")
//     console.log(id);
//     console.log("world")
// })
