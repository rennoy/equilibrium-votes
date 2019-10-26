**Quantitative approach to the EOS block producers election: Your vote counts!** 

There is a growing dissatisfaction raised by the EOS community, and Block Produces (BP) over the BPs election rules: 
questions specifically related to the community’s lack of power in these elections against a concentrated growing BP power.

The [controversy](https://www.coindesk.com/everyones-worst-fears-about-eos-are-proving-true) around the increased concentration 
of China-located EOS holders/voters is on everyone's lips. A small number of BPs that holds or controls the majority of 
circulating EOS represents a significant risk over the network’s integrity. One reason is that 15 colluding BPs have the power 
to approve or reject a new block and trigger a hard fork. 

A BP can vote for its own candidacy, or maliciously bribe voters. It can produce/acquire more EOS with the rewards, or with the 
ill-gotten proceeds from selling their excess EOS votes, so they can secure their dominant position even further. The cycle 
favors concentration and decreases the median voting power. 

One solution to these concerns lies in increasing the number of participants by giving them more incentive to vote.

Voters will be keener to participate in the election if either: 
- their vote is more likely to be decisive. In this case, the votes are cast to benefit the whole community: 
this is a **common welfare economy model**. 
- or if they get a reward out of it: this is a **capitalistic economy**.

But first we need to determine how can we quantify **the power of a voter holding a NUT inventory**. Then we will be able to 
simulate, test and compare various election rules. The ultimate objective is to determine the set of rules that gives every NUT, 
or every NUT holder some measure of voting power. 

However granting such power to a voter must come at a cost. The rules should reasonably avoid giving too much power to voters 
holding a low NUT quantity. Currently, a voter must hold at least two to participate in the “Equilibrium stage”. The 
justification is simple: with great power comes great responsibility (the Peter Parker principle)


**1.1/ The current voting models**

**EOS:**

The number of active block producers is still the topic of hot discussions within the EOS community and the question “Why 21” 
is still actual as well. As a reminder, 21 is a number that Dan Larimer arrived to after some experimentation in his previous 
projects (BitShares, Graphene, and Steem.) For instance, one of Dan Larimer's previous projects, BitShares, had 101 producers, 
and he [mentioned](https://www.youtube.com/watch?v=o7HQlcl-LlQ&feature=youtu.be&t=33m17s) in his interview that this version of 
DPoS raised a significant governance issue as members of the community wouldn't keep up with researching 101 different BPs and 
educate themselves to vote accordingly. Therefore, based on user feedback, he decided on a number that would allow people to stay 
informed while also security via block producing decentralization.

The mechanics are the following: to vote for a block producer within the EOS platform, a voter needs to be an EOS currency holder 
and must stake this EOS holdings to get the right to vote for up to 30 candidates. Each candidate will receive the full amount 
of user’s holdings. Once the votes are cast, the 21 BPs with the highest number of votes will get the most substantial reward. 
They will share 5% of the annual EOS inflation. The remaining candidates will share 15% of the annual inflation. However, among 
them, only those that have a calculated daily reward above 100 EOS will actually receive the reward.

But no need to vote personally. The great EOS feature is that voters can delegate their voting power to so-called proxies. 
These proxies use the amount of EOS they hold to stake and vote. The higher the total EOS staked in Equilibrium, the higher the 
voting power that is delegated to Equilibrium. For instance, in Equilibrium Proxy, the EOS staked are a portion of the EOS 
currency that EOSDT position owners pledge as collateral for their stablecoins. Today, Equilibrium’s stake is over 5,8 mln EOS 
and the amount is continuously growing, making the Equilibrium Proxy one of the largest proxies operating on the network.

**Equilibrium:**

The Equilibrium election is a two-stage voting process. The first stage of the elections, called here the “Equilibrium 
stage” consists of NUT holders participating in the on-chain voting to choose the best candidates for the Equilibrium Proxy. The 
second stage referred to thereafter as the “EOS stage” consists of the Equilibrium Proxy voting for 10 candidates in the EOS election 
according to predefined Proxy rules.

the Equilibrium Proxy rules are the following:

 - **A NUT holder has the right to vote for up to 5 BP candidates**, assigning to each candidate an equal weight that is proportional 
 to the NUT quantity that he holds (1 NUT = 1 vote).
 - Total votes across voters are computed for each BP candidate. **The 10 BPs with the highest total votes are elected**.
In the EOS voting stage, **Equilibrium will vote for the 10 elected BPs**, assigning all the staked EOS to each of 10 BPs.
 - The voting sessions repeat every 10 days.
 
 
**1.2/ How do we measure voting power?**

As John Banzhaf explains in his article [2](http://www.math.ust.hk/~yangwang/Course/2014SSMTH291/Lecture%207%20(Voting)/Banzhaf-one%20man%20n%20votes.pdf): 

> In any voting situation it is possible to consider all the possible ways in which the different voters could vote, i.e., to 
imagine all possible voting combinations. One then asks in how many of these combinations can each voter affect the outcome by 
changing his vote. Since a priori, all voting combinations are equally likely and therefore equally significant, the number of 
combinations in which each voter can change the outcome by changing his vote serves as the measure of his voting power.   

Following the Banzhaf approach, let’s review all scenarios where a vote has an impact (or not) on the outcome of the election. 
For the EOS election, the impact is measured in “rewards”earnings paid daily to the top ranked BPs. And for this exercise, the 
rewards are set as amounts of  “EOS per day”.

**At the “Equilibrium stage”**, if a NUT vote is cast in favor of:

- **a BP that is outside the top 10, and if this vote consequently boosts the BP in the top 10**, then the BP gets additional 
EOS votes for the totality of the EOS staked in Equilibrium (more than 4 mio extra votes). This scenario is referred to as 
**SCENARIO A**.

- **a BP that is outside the top 10, and remains outside the top 10**, then the extra votes would have no impact on the outcome. 
This scenario is referred to as **SCENARIO B**.

- **a BP that is in the top 10**, then the extra votes would have no impact on the outcome. This scenario is referred to as 
**SCENARIO C**.

**At the “EOS stage”**, if extra EOS votes are cast in favor of:

- **one of the top 21 BPs**, it gives the BP an extra reward of 3.59*10-6 EOS per day and per token. This scenario is referred 
to as **SCENARIO 1**.

- **a BP with at least 100 EOS of rewards (i.e. in standby)**, it gives the BP an extra reward of 2.2737*10-6 EOS per day and 
per token, provided the BP stays in standby after the votes. This scenario is referred to as **SCENARIO 2**.

- **a standby BP, and consequently boost the BP to the 21st place**. Then the rewards of the BP are multiplied by a factor of 
1.58. This scenario is referred to as **SCENARIO 3**.

- **a BP with less than 100 EOS in theoretical daily rewards, and consequently boost the BP reward above the 100 threshold**, 
then the rewards of the BP grow from nothing to (initial EOS + extra EOS)∗2.2737*10-6 EOS per day. This scenario is referred to 
as **SCENARIO 4**.

Scenario A from the Equilibrium stage combined with all scenarios in the EOS voting stage (Scenarios 1,2, 3 and 4) are scenarios 
where a NUT voter can have some impact on the election’s outcome. 

The Banzhaf methodology consists here for a voter, with a given quantity of NUT votes, to **count all the possible outcomes, and 
weight them by their financial “reward” impact**. This is our definition of the average voting power for Equilibrium. 

Eventually, we will be looking for the voting parameters that maximize the average voting power.

**1.3/ With great power comes great responsibility** 

**Question: should the votes of a low NUT quantity holder have any impact at all?**

In the last Equilibrium elections, had the proxy voted for 30 BP candidates instead of 10, then voters with a NUT token quantity 
as low as 2 NUTs would have been elected! We are in the early days of the Equilibrium voting process. Only 62 voters turned out 
in the last election. As a result, the choice of up to 5 candidates per voter has been too scarce or too concentrated to provide 
30 winners with a reasonable NUT weight. As far as the EOS election is concerned, with all the controversy around the increasing 
concentration of China-located EOS holders/voters, the “votes concentration” question is on everyone’s lips.The catastrophic scenario 
is the one where a small number of BPs hold the large majority of EOS. Each one of them can vote for their own candidacy, or 
maliciously bribe voters. They can produce/acquire more EOS with the rewards, or with the ill-gotten proceeds from selling their 
excess EOS votes. They can secure their dominant position even further. The cycle favors concentration and decreases the 
median voting power. 

In Equilibrium’s case, **if we open the election outcome to 30 winners instead of 5, it would most likely be much easier 
for a BP (or colluding BPs) to take control of the Equilibrium election outcome, when all that is needed to get the 4 mio 
votes of the 4 mio EOS staked by Equilibrium, is a 2-NUT holding**! We could reasonably consider that there is a much lower 
sense of commitment from a random 2-NUT holder, than a 100-NUT holder. Giving a similar power to both does not look like 
a fair election. This is called the Peter Parker :) principle. On the other hand, a 2-NUT holder is still a member of 
the community, and could be a reasonable voter looking after the best interests of Equilibrium and EOS. If that is indeed 
the case, then regardless of the NUT quantity the voter holds, if several low NUT quantity voters vote the same way, the 
vote should not be considered as random.

In other words, the number of voters holds significant value regardless of the holdings amount. Intuitively, having 10 
voters cast 100 votes each for the same BP holds less ”community” value than having 100 voters cast 10 votes for the same 
BP. All else equal, favoring the BP with the higher number of voters increases the average voting power.

So there are three solutions I can think of: either we give more weight to a candidate with more voters, we still introduce 
a reasonable floor for the quantity of NUT holdings, or we do a mix of both. 

There are always more calls to revise both the EOS and the Equilibrium election process. The above introduction serves as 
a basis for further research at Equilibrium. In the next section, we lay out the quantitative grounds of this research, 
and expose the issues described using the Banzhaf approach. 

**2.1/ Observations**

Let's assume the following notation:

- **the number of votes cast by a voter**: 0 ≤ **v** ≤ V, where V is the maximum number of votes. 
Currently we have V=5 as the maximum number of votes per voter.

- **the number of elected BPs in the proxy stage**: **C**. Currently we have C=10.

- **the quantity of EOS staked by Equilibrium**: **Q**. The quantity staked was EOS 4,977,038 in the last round that we 
analyse here.

- **the number of voters in the proxy stage**: **N**. In our last vote we have N=62.

- **the number of candidates in the proxy stage**: **M**. In our last vote we have 43 BP candidates that received at 
least one NUT vote, but we have in reality much hundreds of candidates listed. We can reduce this number if we consider 
that voting for the low-ranked candidates has 0 voting power, and that a rational voter will not waste a vote or incur a 
cost (time, effort, opportunity cost, etc..) for absolutely no chance of reward. We could draw the line at M=100.

- **the minimal number of NUT needed to cast a vote**: **F**.


One voter can vote for a number of candidates between 0 and V, and can choose among M candidates. He can't vote for the 
same candidate twice. As a result there are #{C(v, M),v=0..V} possible combinations per voter. For N voters, 
the total number of scenarios is these combinations at the power N. 

If the above statement seems like gibberish, let’s get real: For M=100 and r=5, we have hundreds of millions of 
[combinations](https://www.calculatorsoup.com/calculators/discretemathematics/combinations.php)!!

To alleviate our burden, let’s focus on the single reasonable voter: we are only interested in the 5 choices of this particular 
voter. We assume that: 

- the [EOS voting round](https://github.com/rennoy/equilibrium-votes/blob/master/dpos/dpos/src/main/resources/EOSRound.csv) 
is set, and all other [Equilibrium voters have already voted](https://github.com/rennoy/equilibrium-votes/blob/master/dpos/dpos/src/main/resources/EquilibriumRound.csv). 
- the voter who did not vote so far changes his mind, and exercises the full power of his 5 votes. 


We have “only” C(5,100) scenarios! Much more reasonable for my computer! (No fancy Monte-Carlo techniques needed here)

**To simulate outcomes, we need to model the probability laws that govern the electors behavior**. 
Among the observations we would be using:

- **There is a clear concentration of single and 5-choice voters**. 50.8% of the accounts voted for 5 candidates. 
27.9% of the accounts were single voter accounts. The distribution looks like a 
[beta distribution with parameters below 1](https://en.wikipedia.org/wiki/Beta_distribution). 
We can calibrate it with two constraints, one for each tail.

- only one winner (eosiomeetone) was elected with a single vote account. 

- Among the winning candidates, 3 over 10 are in the EOS top 21. Others are in standby. 
**26% of the votes were in favor of the top 21**.
 
- **60.7% of the votes were in favor of the EOS top 70**

- and 6.4% of the votes were given to candidates outside the EOS top 100

**In contrast to a random voting process (i.e. uniform distribution) where each of the 100 BPs candidate has the same one 
hundredth of the votes assigned to him, the distribution of votes here is slightly positively skewed towards the top 21.** 

Also looking at the “NUT Votes per EOS Rank” histogram, **there is no specific concentration of votes around the critical 
21st place or around the 70th place:**

The Equilibrium voters tend to vote for their preferences first, and not specifically for the candidates for which their 
vote can be more decisive, namely candidates near the 21st or 70th place. Their vote carries value anyway, and their 
impact is mostly either one specified in SCENARIOS 1 and 2. 

**2.2/ Simulations**

So to summarize our little exercise: as suggested in the previous version, we chose to model:

- **the stakes per elector distribution** with a 
[gamma distribution](https://github.com/rennoy/equilibrium-votes/blob/master/dpos/dpos/src/main/java/com/eosdt/dpos/service/distributionFit/FitNUTStakesPerElectorGammaDensity.java):

- **the number of votes per elector** with a 
[beta distribution](https://github.com/rennoy/equilibrium-votes/blob/master/dpos/dpos/src/main/java/com/eosdt/dpos/service/distributionFit/FitVotesCountPerElectorBetaDensity.java)

- **the choice of candidates** with a uniform distribution. We could have chosen a regression model (explaining the choice 
with the BP EOS ranking in the previous round) to capture the slight skew towards the 21st top BPs that we observe. But 
that’s good enough to start with...

For the coders, the (rough) simulation code is available on [github](https://github.com/rennoy/equilibrium-votes).

On figure [3], we compute the average and maximum power of an elector for increasing levels of NUT holdings.

**A voter with 760 NUTs will have the maximum potential impact over the EOS election**. Anyone holding more than 760 NUTs 
has the same exact voting power! So if someone is interested in NUTs for the voting process, currently holding 760 NUTs 
is enough to get the best out of NUTs. 

**702 EOS per day distributed among 5 BPs is the maximum impact any Equilibrium voter with 760 NUTs can have on the EOS 
election**. These are not any BPs! They are the 5 BPs for which the elector’s vote will be most decisive. This is an 
extremely substantial power given that the top ranked BP in the EOS election currently benefits from a daily reward that 
is close to 960 EOS per day!

What does a maximum impact (maximum rewards) vote look like? For example 5 votes that are cast for 

- 1/ **eoslambdacom**, 18th position
- 2/ **eosrapidprod**, 22nd position
- 3/ **slowmistiobp**,  23nd position
- 4/ **eosdotwikibp**, 15th position
- 5/ **newdex.bp**, 16th position

will generate for these BPs a “total reward” that is the maximum total reward a voter can give through his vote. 
However this is not the only combination that maximizes the total reward. In fact voting for eosrapidprod and 
slowmistiobp, in combination with any other of the top 21 BPs will produce the same total rewards. 
So the maximum reward is reached when the vote allows for as many standby producers to be in the EOS top 21 block 
producers (this is scenario 3).

If the voter is not interested in aiming for the best rewards assignment, then he can still take comfort in the fact 
that on average over all simulations, the voter’s choice will result in giving 76.21 EOS per day to be distributed 
among the 5 BPs he votes for.

Figure [3] also shows that for a 10 winners Equilibrium election, there isn’t a significant difference in voting power 
between the 2-NUT voter and the 60-NUT voter. In the current setup, raising the minimum required NUT holdings from 2 to 
60 will have no serious incidence on the outcome of the Equilibrium proxy election.


The Equilibrium proxy is a powerful tool to leverage a NUT holder vote in the EOS election. Equilibrium is taking 
serious steps to experiment and ensure that this power will benefit both the EOS and the Equilibrium communities on 
the long-run. The first challenge was to set a reasonable framework inspired for the field of political science to test 
the various election rules that are considered. 

[1] The Mathematics and Statistics of Voting Power, Andrew Gelman, Jonathan N. Katz and Francis Tuerlinckx, 
Statistical Science 2002, Vol. 17, No. 4, 420–435

[2] [One Man, ? Votes](http://www.math.ust.hk/~yangwang/Course/2014SSMTH291/Lecture%207%20(Voting)/Banzhaf-one%20man%20n%20votes.pdf): 
Mathematical Analysis of Voting Power and Effective Representation. John F. Banzhaf
