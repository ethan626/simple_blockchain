import java.util.ArrayList;
import java.time.Instant; 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import flexjson.JSONSerializer;

public class BlockChain{
    ArrayList <Block> chain = new ArrayList();
    ArrayList currentTransactions = new ArrayList();
    
    public BlockChain(){
	// Instantiate the genesis block
    }

    public Block newBlock(int proof, String previousHash){
	// Create a new block and add the new block to the chain
	// proof is the given by the Proof of Work algorithm
	// previous hash is the hash of the previous block
	// returns a Block object
	Instant timestamp = Instant.now();
	currentTransactions.clear();
	Block block = new Block(this.chain.size(), timestamp, currentTransactions, proof, previousHash);
	this.chain.add(block);
	return block;
    }

    public int newTransaction(String sender, String recipient, float amount){
	// Add a new transaction to the list of transactions
	// sender is the address of the sender
	// recipient is the address of the recipient
	// amount is the amount of the transaction
	// Returns the index of the newly created transaction
	Transaction transaction = new Transaction(sender, recipient, amount);
	currentTransactions.add(transaction);
	return this.chain.size() - 1;	// Index of the newly created block
    }

    public Block lastBlock() {
	// Returns the last block in the chain
	// Returns null if a there are no blocks. 
	try {
	    Block block = this.chain.get(this.chain.size() - 1);
	    return block;
	}
	catch(IndexOutOfBoundsException e) {
		return null;
	}
    }
    
    public String hash(String data) throws NoSuchAlgorithmException {
	// Hashes a block
	MessageDigest md = MessageDigest.getInstance( "SHA-256" );
	byte[] blockBytes = data.getBytes();
	return md.digest(blockBytes).toString();
    }

    public int proofOfWork(int lastProof){
	// Simple proof of work algorithm
	// last proof is the proof from the previous transaction
	int proof = 0;
	while(validProof(lastProof, proof) == false){
	    proof += 1;
	};
	
	return proof;
    }
    
    public boolean validProof(int lastProof, int proof) {
	    // Does hash(lastProof) meet the criteria?
	    // Returns True if so, else False
	try {
	    String guess = Integer.toString(lastProof) + Integer.toString(proof);
	    String guessHash = hash(guess);
	    
	    if (guessHash.substring(0, 4) == "0000") {
		return true;
	    }
	    else {
		return false;
	    }
	}
	catch(NoSuchAlgorithmException  e){
	    System.out.println("No such encryption algorithm");
	    return false;
	}
    }
}
	   
