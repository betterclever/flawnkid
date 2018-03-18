pragma solidity ^0.4.18;


contract GameContest {

  struct StakeHolder {
    address addr;
    uint amount;
    bool claimed;
  }

  struct Submission {
    address addr;
    string previewLink;
  }

  struct GameIdea {
    string topic;
    string description;
    uint numStakeHolders;
    uint numSubmissions;
    uint amount;
    mapping (uint => StakeHolder) stakeHolders;
    mapping (uint => Submission) submissions;
  }
  
  uint numGameIdeas;

  event newIdeaSubmitted(uint ideaID);

  mapping (uint => GameIdea) gameIdeas;

  function newGameIdea(string topic, string description)  {
    uint gameIdeaID = numGameIdeas++; 
    emit newIdeaSubmitted(gameIdeaID);
    gameIdeas[gameIdeaID] = GameIdea(topic, description, 0, 0, 0);
  }

  function addStake(uint gameIdeaID) payable {
    GameIdea c = gameIdeas[gameIdeaID];
    c.stakeHolders[c.numStakeHolders++] = StakeHolder({addr: msg.sender, amount: msg.value, claimed: false});
    c.amount += msg.value;
  }

  function addSubmission(uint gameIdeaID, string previewLink) returns (uint submissionID) {
    GameIdea idea = gameIdeas[gameIdeaID];
    submissionID = idea.numSubmissions++;
    idea.submissions[submissionID] = Submission(
       msg.sender, previewLink
    );
    return submissionID;
  }

  function buySubmission(uint gameIdeaID, uint submissionID) returns(bool status) {
    GameIdea idea = gameIdeas[gameIdeaID];
    Submission s = idea.submissions[submissionID];

    for (uint index = 0; index < idea.numStakeHolders; index++) {
      StakeHolder st = idea.stakeHolders[index];
      if(st.addr == msg.sender) {
        if(!st.claimed) {
          s.addr.send(st.amount);
          idea.amount -= st.amount;
          st.claimed = true;
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }

    return false;
  }
  

  // function checkGoalReached(uint gameIdeaID) returns (bool reached) {
  //   GameIdea c = gameIdeas[gameIdeaID];
  //   if (c.amount < c.fundingGoal)
  //     return false;
  //   c.beneficiary..send(c.amount);
  //   c.amount = 0;
  //   return true;
  // }

}