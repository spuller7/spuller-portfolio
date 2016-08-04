using UnityEngine;
using System.Collections;

public class cutterAnimation : MonoBehaviour {

    private Animator anim;
    private bool mDown;

    // Use this for initialization
    void Start () {
        anim = GetComponent<Animator>();
    }
	
	// Update is called once per frame
	void Update () {
        if (Input.GetKeyDown(KeyCode.A))
        {
            anim.SetBool("aDown", true);

        }
        if (Input.GetKeyUp(KeyCode.A))
        {
            anim.SetBool("aDown", false);

        }
        if (Input.GetKeyDown(KeyCode.D))
        {
            anim.SetBool("dDown", true);

        }
        if (Input.GetKeyUp(KeyCode.D))
        {
            anim.SetBool("dDown", false);

        }
    }
}
