pragma solidity ^0.4.18;


contract GameContest {

  struct StakeHolder {
    address addr;
    uint amount;
  }

  struct GameIdea {
    string topic;
    string description;
    uint numStakeHolders;
    uint amount;
    mapping (uint => StakeHolder) stakeHolders;
  }
  
  uint numGameIdeas;
  mapping (uint => GameIdea) gameIdeas;

  function newGameIdea(string topic,
                      string description
                      ) returns (uint gameIdeaID) 
  {
    gameIdeaID = numGameIdeas++; 
    gameIdeas[gameIdeaID] = GameIdea(topic, description, 0, 0);
    GameIdea g = gameIdeas[gameIdeaID];
    g.stakeHolders[g.numStakeHolders++] = StakeHolder({addr: msg.sender, amount: msg.value});
    g.amount += msg.value;
  }
  
  function addStake(uint gameIdeaID) {
    GameIdea c = gameIdeas[gameIdeaID];
    c.stakeHolders[c.numStakeHolders++] = StakeHolder({addr: msg.sender, amount: msg.value});
    c.amount += msg.value;
  }

  // function checkGoalReached(uint gameIdeaID) returns (bool reached) {
  //   GameIdea c = gameIdeas[gameIdeaID];
  //   if (c.amount < c.fundingGoal)
  //     return false;
  //   c.beneficiary.send(c.amount);
  //   c.amount = 0;
  //   return true;
  // }

}