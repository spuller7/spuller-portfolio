using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class ShipShooting : ShipBase
{

    public List<GameObject> cutterLeftCannons = new List<GameObject>();
    public List<GameObject> cutterRightCannons = new List<GameObject>();
    public List<GameObject> brigLeftCannons = new List<GameObject>();
    public List<GameObject> brigRightCannons = new List<GameObject>();
    public List<GameObject> doubleRightCannons = new List<GameObject>();
    public List<GameObject> doubleLeftCannons = new List<GameObject>();
    private List<GameObject> shipRightSide;
    private List<GameObject> shipLeftSide;
    private List<GameObject> activeCannonList;
    private List<GameObject> cutterRange;
    public float ShootDelay;
    public GameObject projectile;
    public int damage = 25;
    public Transform playerObject;
    static int distanceNum;
    float waitTime;
    public GameObject muzzleSmo;

    private int currentSide = 0;

    void Start()
    {
        activeCannonList = cutterRightCannons;
        shipLeftSide = cutterLeftCannons;
        shipRightSide = cutterRightCannons;
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetButtonDown("Fire Broadside"))
        {
            fire();
        }
        if (Input.GetButtonDown("Switch Port Side"))
        {
            switchCannons(1);
        }
        if (Input.GetButtonDown("Switch Starboard Side"))
        {
            switchCannons(0);
        }
    }

    private void fire()
    {
        foreach (GameObject transform in activeCannonList)
        {
            shooting(transform.transform);
        }
    }

    private void shooting(Transform t)
    {
        waitTime = Random.Range(0.0F, 2.0F);
        StartCoroutine(Example(waitTime, t));
    }

    IEnumerator Example(float wait, Transform t)
    {

        yield return new WaitForSeconds(wait);
        OnShoot(t);
    }

    public void OnShoot(Transform t)
    {
        //GameObject newProjectileObject = (GameObject)PhotonNetwork.Instantiate("Muzzlesmoke", t.transform.position, t.transform.rotation, 0);
        GameObject newProjectileObject =  PhotonNetwork.Instantiate("Muzzlesmoke", t.transform.position, t.transform.rotation, 0);

        Ray ray = new Ray(transform.position, transform.forward);
        Transform hitTransform;
        Vector3 hitPoint;

        hitTransform = FindClosestHitObject(ray, out hitPoint);
        if (hitTransform != null)
        {
            Debug.Log("Hit: " + hitTransform.name);

            // We could do a special effect at the hit location
            // DoRicochetEffectAt( hitPoint );

            Health h = hitTransform.GetComponent<Health>();

            while (h == null && hitTransform.parent)
            {
                hitTransform = hitTransform.parent;
                h = hitTransform.GetComponent<Health>();

            }

            // Once we reach here, hitTransform may not be the hitTransform we started with!

             if (h != null)
             {
                 PhotonView pv = h.GetComponent<PhotonView>();
                 if (pv == null)
                 {
                     Debug.LogError("Freak out!");
                 }
                 else
                 {
                     if (hitTransform != playerObject)
                     {
                         h.TakeDamage(health.healthDamage, health.speedDamage, health.crewDamage);
                         //h.GetComponent<PhotonView>().RPC("TakeDamage", PhotonTargets.All, damage);
                     }
                 }
             }


        }
    }
    Transform FindClosestHitObject(Ray ray, out Vector3 hitPoint)
    {

        RaycastHit[] hits = Physics.RaycastAll(ray);

        Transform closestHit = null;
        float distance = 0;
        hitPoint = Vector3.zero;

        foreach (RaycastHit hit in hits)
        {
            if (hit.transform != this.transform && (closestHit == null || hit.distance < distance))
            {
                // We have hit something that is:
                // a) not us
                // b) the first thing we hit (that is not us)
                // c) or, if not b, is at least closer than the previous closest thing

                closestHit = hit.transform;
                distance = hit.distance;
                hitPoint = hit.point;
            }
        }

        // closestHit is now either still null (i.e. we hit nothing) OR it contains the closest thing that is a valid thing to hit

        return closestHit;

    }
    private void switchCannons(int n)
    {
        if (n == 0 && currentSide == 1)
        {
            activeCannonList = shipRightSide;
            currentSide = 0;
        }
        else if (n == 1 && currentSide == 0)
        {
            currentSide = 1;
            activeCannonList = shipLeftSide;
        }
        else
        {
            return;
        }
    }
    private void changeRange(int range)
    {
        foreach (GameObject transform in activeCannonList)
        {
            shooting(transform.transform);
        }
    }

}
