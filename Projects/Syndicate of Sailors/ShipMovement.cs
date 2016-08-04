using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class ShipMovement : ShipBase
{
	
	public float Speed = 0.0f;
	public float acceleration = 1.0f;
	public float maxspeed = 12.0f;
	public float minspeed = -0.25f;
	Vector3 m_NetworkPosition;
	Quaternion m_NetworkRotation;
	double m_LastNetworkDataReceivedTime;
	
	
	void Update(){
		
		if( GamemodeManager.CurrentGamemode.IsRoundFinished() == true )
		{
			return;
		}
		
		if( PhotonView.isMine == true )
		{
			//if( Ship.Health > 0 )	{
			//UpdateImpactMovement();
			UpdateMovement();
			//UpdateRotation();
			//UpdatePosition();
			//}
		}
		else
		{
			UpdateNetworkedPosition();
			UpdateNetworkedRotation();
		}
		
	}
	
	void UpdateNetworkedPosition()
	{
		//Here we try to predict where the player actually is depending on the data we received through the network
		//Check out Part 1 Lesson 2 http://youtu.be/7hWuxxm6wsA for more detailed explanations
		float pingInSeconds = (float)PhotonNetwork.GetPing() * 0.001f;
		float timeSinceLastUpdate = (float)( PhotonNetwork.time - m_LastNetworkDataReceivedTime );
		float totalTimePassed = pingInSeconds + timeSinceLastUpdate;
		
		Vector3 exterpolatedTargetPosition = m_NetworkPosition
			+ transform.forward * Speed * totalTimePassed;
		
		
		Vector3 newPosition = Vector3.MoveTowards( transform.position
		                                          , exterpolatedTargetPosition
		                                          , Speed * Time.deltaTime );
		
		if( Vector3.Distance( transform.position, exterpolatedTargetPosition ) > 2f )
		{
			newPosition = exterpolatedTargetPosition;
		}
		
		newPosition.y = Mathf.Clamp( newPosition.y, 0.5f, 50f );
		
		transform.position = newPosition;
	}
	
	void UpdateNetworkedRotation()
	{
		transform.rotation = Quaternion.RotateTowards(
			transform.rotation,
			m_NetworkRotation, 180f * Time.deltaTime
			);
	}
	
	/*void signedSqrt(int x ){
		var r = Mathf.Sqrt(Mathf.Abs( x ));
		if( x < 0 ){
			return -r;
		} else {
			return r;
		}
	}*/
	
	void UpdateMovement () {
		
		Speed += Input.GetAxis("VerticalKeyboard") * acceleration * Time.deltaTime;
		if( Speed > maxspeed ){
			Speed = maxspeed;
		} else if ( Speed < minspeed ){
			Speed = minspeed;
		}
		
	}
	
	
	public void SerializeState( PhotonStream stream, PhotonMessageInfo info )
	{
		//We only need to synchronize a couple of variables to be able to recreate a good
		//approximation of the ships position on each client
		//There is a lot of smoke and mirrors happening here
		//Check out Part 1 Lesson 2 http://youtu.be/7hWuxxm6wsA for more detailed explanations
		if( stream.isWriting == true )
		{
			stream.SendNext( transform.position );
			stream.SendNext( transform.rotation );
			stream.SendNext( Speed );
			//stream.SendNext( m_IsMovementUpsideDown );
		}
		else
		{
			m_NetworkPosition = (Vector3)stream.ReceiveNext();
			m_NetworkRotation = (Quaternion)stream.ReceiveNext();
			Speed = (float)stream.ReceiveNext();
			//m_IsMovementUpsideDown = (bool)stream.ReceiveNext();
			
			m_LastNetworkDataReceivedTime = info.timestamp;
		}
	}
	
}
