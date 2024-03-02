export function calculateDistance(lat1: number, lon1: number, lat2: number, lon2: number): string {
    const earthRadius = 6371000; // 地球半径（单位：米）
  
    const toRadians = (degrees: number) => (degrees * Math.PI) / 180;
    
    const dLat = toRadians(lat2 - lat1);
    const dLon = toRadians(lon2 - lon1);
  
    const a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
  
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const d = earthRadius * c;
    
    const distance = Math.floor(d);
    if(distance < 1000) {
        return `${distance}m`;
    }else {
        const kilometers = (distance / 1000).toFixed(0);
        return `${kilometers}km`;
    }
  }